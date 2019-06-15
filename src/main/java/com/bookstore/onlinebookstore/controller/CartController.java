package com.bookstore.onlinebookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bookstore.onlinebookstore.service.CartService;

@Controller
@RequestMapping("/cart")
@SessionAttributes({ "cart" })
public class CartController {
	@Autowired
	private CartService cartService;

	@PostMapping("/cart.do")
	public String cartItem(ModelMap modelMap, HttpServletRequest request) {
		cartService.addItemToCart(modelMap, request);
		return "redirect:/cart/view";
	}

	@RequestMapping("/view")
	public String getShoppingCart(ModelMap modelMap) {
		return "shopping-cart";
	}

	@PostMapping("/checkout")
	public String checkoutCart() {
		return "checkout";
	}
}