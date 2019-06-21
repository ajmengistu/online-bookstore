package com.bookstore.onlinebookstore.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.onlinebookstore.model.Address;
import com.bookstore.onlinebookstore.model.Item;
import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.service.AddressService;
import com.bookstore.onlinebookstore.service.OrderService;
import com.bookstore.onlinebookstore.service.OrderedBookService;
import com.bookstore.onlinebookstore.service.PasswordResetTokenService;
import com.bookstore.onlinebookstore.service.UserService;

@Controller
@RequestMapping("/account")
public class CustomerAccountController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private OrderedBookService orderedBookService;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;

	@RequestMapping("/order-details")
	public String getOrderDetails(@RequestParam String orderID, ModelMap modelMap, HttpServletRequest request,
			Principal principal) {
		if (orderID != null) {
			System.out.println(orderID);
			String hash = orderID;
			User user = (User) request.getSession().getAttribute("user");
			Order order = orderService.getOrderByHashAndByUserId(hash, user.getId());
			if (order != null) {
				modelMap.put("orderedDate", orderService.formatDate(order.getDateOrdered()));
				modelMap.put("order", order);

				Address address = addressService.getAddressById(order.getAddressId());
				modelMap.put("userAddress", address);
				
				List<Item> orderedBooks = orderedBookService.getOrderedBooksByOrderId(order.getOrderId());
				modelMap.put("orderedBooks", orderedBooks);
				
				modelMap.put("totalItemsOrdered", orderedBookService.getTotalQuantity(orderedBooks));
			} else {
				return "redirect:/account/order-history";
			}
		}
		return "order-details";
	}

	@RequestMapping("/order-history")
	public String getOrderHistory(ModelMap modelMap, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		System.out.println(user);
		HashMap<Order, List<Item>> orders = orderedBookService
				.getOrderedBooks(orderService.getAllOrdersByUserId(user.getId()));
		modelMap.put("orders", orders);
		System.out.println(orders);

		return "order-history";
	}

	@RequestMapping("/profile")
	public String getAccountProfile(ModelMap modelMap, HttpSession session) {
		String newEmail = (String) modelMap.get("newEmail");
		if (newEmail != null) {
			User user = userService.getCurrentUserByEmail(newEmail);
			session.setAttribute("user", user);
		}

		return "account-profile";
	}

	@PostMapping("/password/update")
	public String updateAccountPassword(RedirectAttributes redirectAttr, HttpServletRequest request,
			Principal principal) {
		userService.updateUserAccountPassword(request, redirectAttr, principal.getName());
		return "redirect:/account/profile";
	}

	@PostMapping("/email/update")
	public String updateAccountEmail(RedirectAttributes redirectAttr, HttpServletRequest request, Principal principal) {
		userService.updateUserAccountEmail(request, redirectAttr, principal.getName());
		return "redirect:/account/profile";
	}

	@RequestMapping("/recommendations")
	public String getAccountRecommendations(Principal principal, ModelMap modelMap) {
		userService.getAccountUserRecommendations(principal, modelMap);
		return "recommendations";
	}

	@RequestMapping("/password/reset")
	@PostMapping("/password/reset.do")
	public String changePassword(ModelMap modelMap, HttpServletRequest request) {
		if (request.getParameter("email") != null) {
			return passwordResetTokenService.resetPasswordByEmail(request.getParameter("email"), modelMap);
		}
		return "change-password";
	}

	@RequestMapping("/password/reset/")
	public String resetPassword(@RequestParam String token, ModelMap modelMap) {
		if (token != null) {
			passwordResetTokenService.verifyToken(token, modelMap);
		}
		return "reset-password";
	}

	@PostMapping("/password/reset/change.do")
	public String updatePassword(ModelMap modelMap, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		return passwordResetTokenService.updatePassword(modelMap, request, redirectAttributes);
	}

}