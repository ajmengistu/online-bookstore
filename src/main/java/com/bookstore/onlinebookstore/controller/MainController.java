package com.bookstore.onlinebookstore.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.model.forms.AccountRegistrationForm;
import com.bookstore.onlinebookstore.repository.UserRepository;
import com.bookstore.onlinebookstore.service.BookService;
import com.bookstore.onlinebookstore.service.ShoppingCartService;
import com.bookstore.onlinebookstore.service.UserService;

@Controller
@SessionAttributes({ "URL_REF", "user", "cart" })
public class MainController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BookService bookService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private UserService userService;

	@RequestMapping("/query")
	public @ResponseBody List<Book> getQueryResults(@RequestParam String q) {
		return bookService.getQueryResults(q);
	}

	@RequestMapping("/search")
	public String getQuerySearch(@RequestParam String q, ModelMap modelMap) {
		modelMap.put("query", q);
		modelMap.put("title", q + " - " + "OnlineBookstore");
		List<Book> list = bookService.getSearchResults(q);
		modelMap.put("numOfResults", list.size());
		modelMap.put("searchResultList", list);
		return "search-results";
	}

	@RequestMapping("/")
	public String userHomePage(Model model, ModelMap modelMap) {
		// List of Books
		modelMap.put("topPopularBooksList", bookService.getPopularBooks());
		modelMap.put("topRatedBooksList", bookService.getTopRatedBooks());
		modelMap.put("topRatedBooksByYearList", bookService.getTopRatedBooksByYear());
		modelMap.put("ancientLitBooksList", bookService.getAncientLiteratureBooks());
		return "home";
	}

	@RequestMapping("/register")
	public String userRegistrationPage(ModelMap modelMap, AccountRegistrationForm accountRegistrationForm) {
		return "register";
	}

	@PostMapping("/register.do") // register/new/user
	public String validateUserRegistrationForm(ModelMap modelMap, HttpServletRequest request,
			RedirectAttributes redirectAttr, @Valid AccountRegistrationForm accountRegistrationForm) {
		return userService.createNewAccount(accountRegistrationForm, modelMap, redirectAttr);
	}

	@RequestMapping("/login")
	public String userLoginPage(ModelMap modelMap, HttpServletRequest request) {
		modelMap.put("userLogin", "/login.do");
		System.out.println("Referer: " + request.getHeader("Referer"));
		modelMap.addAttribute("URL_REF", request.getHeader("Referer"));
		return "login";
	}

	@RequestMapping("/account")
	public String userAccountPage(ModelMap modelMap, Principal principal) {
		modelMap.put("welcomeMessage", "Welcome!");
		return "account";
	}

	@RequestMapping("/admin")
	public String adminHomePage(ModelMap modelMap) {
		return "admin";
	}

	@RequestMapping("/loginSuccessful")
	public String userLoginSuccessful(@RequestParam String role, ModelMap modelMap, HttpServletRequest request,
			Principal principal) {
		User user = userRepo.findByEmail(principal.getName());
		user.setPassword(null);

		modelMap.put("user", user);
		Cart cart = shoppingCartService.getSavedUserShoppingCart(user);

		modelMap.put("cart", cart);

		String url_ref = request.getSession().getAttribute("URL_REF").toString();
		String url = null;
		if (url_ref != null && url_ref.contains("/cart/view")) {
			System.out.println("-------------------------------" + url_ref);
			url = "redirect:/cart/checkout";
		} else {
			System.out.println("hello: " + url_ref);
			url = "redirect:/cart/checkout";
		}
		return url;
	}

	@RequestMapping("/logoutSuccessful")
	public String userLogoutValidation(ModelMap modelMap, RedirectAttributes redirectAttr) {
		System.out.println("You have logged out!");
		redirectAttr.addFlashAttribute("LOGOUT_SUCCESSFUL_MESSAGE", "Success! You have successfully logged out.");
		return "redirect:/login";
	}
}