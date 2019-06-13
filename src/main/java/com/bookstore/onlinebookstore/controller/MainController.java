package com.bookstore.onlinebookstore.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.model.enums.RoleType;
import com.bookstore.onlinebookstore.repository.BookRepository;
import com.bookstore.onlinebookstore.repository.UserRepository;
import com.bookstore.onlinebookstore.service.BookService;

@Controller
public class MainController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private BookService bookService;

	@Autowired
	private BookRepository bookRepository;

	@RequestMapping("/top")
	public @ResponseBody List<Book> getAll(@RequestParam String q) {
		System.out.println(q);
//		return bookRepository.findByTitleContaining(q);
		return bookRepository.findByTitleContainingOrAuthorsContaining(q, q);
	}

	@RequestMapping("/test")
	public String test() {
		return "test";
	}

	@RequestMapping("/")
	public String userHomePage(Model model, ModelMap modelMap) {
		modelMap.put("userLogin", "/login");
		modelMap.put("userRegister", "/register");
		modelMap.put("home", "/");
		modelMap.put("topPopularBooksList", bookService.getPopularBooks());
		return "home";
	}

	@RequestMapping("/register")
	public String userRegistrationPage(ModelMap modelMap) {
		modelMap.put("reg", "/register.do");
		return "register";
	}

	@PostMapping("/register.do")
	public String validateUserRegistrationForm(ModelMap modelMap, HttpServletRequest request,
			RedirectAttributes redirectAttr) {
		System.out.println(request.getParameter("firstName"));
		System.out.println(request.getParameter("lastName"));
		System.out.println(request.getParameter("email"));
		System.out.println(request.getParameter("password"));
		System.out.println(request.getParameter("verifyPassword"));
		User user = new User(request.getParameter("firstName"), request.getParameter("lastName"),
				request.getParameter("email"), passwordEncoder.encode(request.getParameter("password")), new Date(),
				RoleType.CUSTOMER);
		userRepo.save(user);
		redirectAttr.addFlashAttribute("REGISTRATION_SUCCESSFUL_MESSAGE",
				"Success! You have successfully registered for an account.");
		return "redirect:/login"; // send a registration successful message/hidden input to login page
	}

	@RequestMapping("/login")
	public String userLoginPage(ModelMap modelMap, HttpServletRequest request) {
		modelMap.put("userLogin", "/login.do");
		System.out.println("Referer: " + request.getHeader("Referer"));
		return "login";
	}

	@RequestMapping("/account")
	public String userAccountPage(ModelMap modelMap, Principal principal) {
		System.out.println("Account requst handler: ");
		modelMap.addAttribute("welcomeMessage", "Welcome!").addAttribute("user",
				userRepo.findByEmail(principal.getName()));
		return "account";
	}

	@RequestMapping("/admin")
	public String adminHomePage(ModelMap modelMap) {

		return "admin";
	}

	@RequestMapping("/logoutSuccessful")
	public String userLogoutValidation(ModelMap modelMap, RedirectAttributes redirectAttr) {
		System.out.println("You have logged out!");
		redirectAttr.addFlashAttribute("LOGOUT_SUCCESSFUL_MESSAGE", "Success! You have successfully logged out.");
		return "redirect:/login";
	}
}