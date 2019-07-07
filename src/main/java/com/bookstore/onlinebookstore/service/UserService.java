package com.bookstore.onlinebookstore.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.onlinebookstore.model.Address;
import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Item;
import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.model.enums.RoleType;
import com.bookstore.onlinebookstore.model.forms.AccountRegistrationForm;
import com.bookstore.onlinebookstore.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderedBookService orderedBookService;
	@Autowired
	private BookService bookService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private AddressService addressService;

	public void updateUserAccountPassword(HttpServletRequest request, RedirectAttributes redirectAttr, String email) {
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		if (!newPassword.equals(confirmPassword)) {
			redirectAttr.addFlashAttribute("ERROR", "Error! Your password did not match. Please try again.");
		} else {
			if (isCurrentPasswordValid(currentPassword, email)) {
				updateUserPassword(newPassword, email);
				redirectAttr.addFlashAttribute("SUCCESS", "Success! Your password has been updated.");
			} else {
				redirectAttr.addFlashAttribute("ERROR", "Error! Your current password is invalid. Please try again.");
			}
		}
	}

	private void updateUserPassword(String newPassword, String email) {
		User user = getCurrentUserByEmail(email);
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	public void updateUserPassword(String newPassword, Long userId) {
		User user = getUserByUserId(userId);
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	public User getUserByUserId(Long userId) {
		return userRepository.findById(userId).get();
	}

	public boolean isCurrentPasswordValid(String password, String email) {
		System.out.println(passwordEncoder.matches(password, getPasswordByEmail(email)));
		if (passwordEncoder.matches(password, getPasswordByEmail(email))) {
			return true;
		} else {
			return false;
		}
	}

	public String getPasswordByEmail(String email) {
		return userRepository.findByEmail(email).getPassword();
	}

	public User getCurrentUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void updateUserAccountEmail(HttpServletRequest request, RedirectAttributes redirectAttr, String email) {
		String currentEmail = request.getParameter("currentEmail");
		String newEmail = request.getParameter("newEmail");
		String confirmEmail = request.getParameter("confirmEmail");

		if (!newEmail.equals(confirmEmail)) {
			redirectAttr.addFlashAttribute("ERROR", "Error! Your email did not match. Please try again.");
		} else {
			if (currentEmail.equals(email)) {
				if (updateUserEmail(newEmail, currentEmail)) {
					redirectAttr.addFlashAttribute("SUCCESS", "Success! Your email has been updated.");
					redirectAttr.addFlashAttribute("newEmail", newEmail);
				} else {
					redirectAttr.addFlashAttribute("ERROR",
							"Error! The provided new email already exists. Please choose a different email.");
				}
			} else {
				redirectAttr.addFlashAttribute("ERROR", "Error! The provided email does not exist. Please try again.");
			}
		}

	}

	public boolean updateUserEmail(String newEmail, String currentEmail) {
		User user = getCurrentUserByEmail(newEmail);
		if (user == null) {
			User user2 = getCurrentUserByEmail(currentEmail);
			user2.setEmail(newEmail);
			userRepository.save(user2);
			return true;
		} else {
			return false;
		}

	}

	public void getAccountUserRecommendations(Principal principal, ModelMap modelMap) {
		User user = getCurrentUserByEmail(principal.getName());
		List<Book> recommendedBooks = new ArrayList<>();
		List<Book> allOrderedBooks = new ArrayList<>();

		List<Order> orders = orderService.getAllOrdersByUserId(user.getId());
		for (Order order : orders) {
			List<Item> orderedBooks = orderedBookService.getOrderedBooksByOrderId(order.getOrderId());
			allOrderedBooks.addAll(getAllBooksFromItems(orderedBooks));
			for (Item item : orderedBooks) {
				for (Book book : bookService.getBooksByAuthor(item.getBook().getAuthors())) {
					if (!allOrderedBooks.contains(book))
						recommendedBooks.add(book);
					if (recommendedBooks.size() > 25) {
						break;
					}
				}
				if (recommendedBooks.size() > 25) {
					break;
				}
			}
			if (recommendedBooks.size() > 25) {
				break;
			}
		}
		modelMap.put("recommendedBooks", recommendedBooks);
	}

	private List<Book> getAllBooksFromItems(List<Item> orderedBooks) {
		List<Book> list = new ArrayList<>();
		for (Item item : orderedBooks) {
			list.add(item.getBook());
		}
		return list;
	}

	public String createNewAccount(@Valid AccountRegistrationForm accountRegistrationForm, ModelMap modelMap,
			RedirectAttributes redirectAttr) {
		if (!accountRegistrationForm.getPassword().equals(accountRegistrationForm.getConfirmPassword())) {
			modelMap.put("ERROR", "Error! Your password does not match. Please try again.");
			return "register";
		} else {
			if (userRepository.findByEmail(accountRegistrationForm.getEmail()) != null) {
				modelMap.put("ERROR", "Error! The email provided already exists. Please try again.");
				return "register";
			} else {
				createNewUser(accountRegistrationForm);
				redirectAttr.addFlashAttribute("REGISTRATION_SUCCESSFUL_MESSAGE",
						"Success! You have successfully registered for an account.");
				sendNewUserEmailWelcome(accountRegistrationForm);
				return "redirect:/login";
			}
		}
	}

	private void sendNewUserEmailWelcome(@Valid AccountRegistrationForm accountRegistrationForm) {
		String to = accountRegistrationForm.getEmail();
		String from = "OnlineBookstore.com <auto-confirm@onlinebookstore.com>";
		String subject = accountRegistrationForm.getFirstName() + ", welcome to OnlineBookstore!";
		String body = accountRegistrationForm.getFirstName() + ", welcome to OnlineBookstore!\r\n" + "\r\n"
				+ "Browse your favorite books, our editorial picks, bestsellers, or customer favorites.\r\n" + "\r\n";
		emailService.sendSimpleMessage(to, from, subject, body);
	}

	private void createNewUser(@Valid AccountRegistrationForm accountRegistrationForm) {
		User user = new User();
		user.setFirstName(accountRegistrationForm.getFirstName());
		user.setLastName(accountRegistrationForm.getLastName());
		user.setEmail(accountRegistrationForm.getEmail());
		user.setPassword(passwordEncoder.encode(accountRegistrationForm.getPassword()));
		user.setUserRole(RoleType.CUSTOMER);
		user.setDateCreated(new Date());
		userRepository.save(user);
	}

	public void sendOrderDetailsEmail(String hash, User user) {
		Order order = orderService.getOrderByHash(hash);
		Address address = addressService.getAddressById(order.getAddressId());

		String to = user.getEmail();
		String from = "OnlineBookstore.com <auto-confirm@onlinebookstore.com>";
		String subject = "Your OnlineBookstore.com Order Details";
		String body = "Hello " + user.getFirstName() + " " + user.getLastName() + ",\n\n"
				+ "Thank you for shopping with us. We’ll send a confirmation when your item ships.\n\n" + "Details: \n"
				+ "Order #: " + hash + "\n\n" + "Ship to: \n" + "" + address.getFirstName() + " "
				+ address.getLastName() + "\n" + address.getAddress1() + " " + address.getAddress2() + "\n"
				+ address.getCity() + ", " + address.getState() + " " + address.getZip() + "\n\n"
				+ "Total Before Tax:    $" + order.getTotal() + "\n" + "Estimated Tax:    $0.00\n" + "Order Total:    $"
				+ order.getTotal() + "\n\n" + "We hope to see you again soon.\r\n" + "\r\n" + "OnlineBookstore.com \r\n"
				+ "";
		emailService.sendSimpleMessage(to, from, subject, body);
	}

}
