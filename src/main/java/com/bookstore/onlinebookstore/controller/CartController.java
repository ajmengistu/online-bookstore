package com.bookstore.onlinebookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.service.AddressService;
import com.bookstore.onlinebookstore.service.CartService;

@Controller
@RequestMapping("/cart")
@SessionAttributes({ "cart" })
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private AddressService addressService;

	/*
	 * ******* Generate a BrainTreeGateway token for payment transaction ******
	 * 
	 * @RequestMapping(value = "/cart/braintree/cltoken", method =
	 * RequestMethod.POST, produces = "application/json") public @ResponseBody
	 * Map<String, String> getClientToken() { BraintreeGateway gateway =
	 * processPaymentService.getBrainTreeGateway(); ClientTokenRequest
	 * clientTokenRequest = new ClientTokenRequest(); String clientToken =
	 * gateway.clientToken().generate(clientTokenRequest); HashMap<String, String>
	 * map = new HashMap<>(); map.put("clientToken", clientToken); return map; }
	 */
	@PostMapping("/cart.do")
	public String cartItem(ModelMap modelMap, HttpServletRequest request) {
		cartService.addItemToCart(modelMap, request);
		return "redirect:/cart/view";
	}

	@PostMapping("/update")
	public String updateItem(ModelMap modelMap, HttpServletRequest request) {
		cartService.updateItem(modelMap, request);
		return "redirect:/cart/view";
	}

	@RequestMapping("/view")
	public String getShoppingCart(ModelMap modelMap) {
		return "shopping-cart";
	}

	@RequestMapping("/checkout")
	@PostMapping("/checkout")
	public String checkoutCart(ModelMap modelMap, HttpServletRequest request) {
		// if shopping cart is empty redirect user to the home page
		// mostly recently used shipping address
		User user = (User) request.getSession().getAttribute("user");
		addressService.getRecentlyUsedAddress(modelMap, user.getId());
		return "checkout";
	}

	@PostMapping("/address/add")
	public String addNewShippingAddress(ModelMap modelMap, HttpServletRequest request) {
		System.out.println(request.getParameter("inputAddress"));
		System.out.println(request.getParameter("inputAddress2"));
		System.out.println(request.getParameter("inputCity"));
		System.out.println(request.getParameter("inputState"));
		System.out.println(request.getParameter("inputZip"));
//		addressService.addNewAddress();
		return "redirect:/cart/checkout";
	}
}