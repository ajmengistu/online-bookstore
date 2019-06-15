package com.bookstore.onlinebookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.Item;
import com.bookstore.onlinebookstore.service.BookService;

@Controller
@RequestMapping("/cart")
@SessionAttributes({ "cart" })
public class CartController {
	@Autowired
	private BookService bookService;

	@PostMapping("/cart.do")
	public String cartItem(RedirectAttributes redirectAttr, ModelMap modelMap, HttpServletRequest request) {
		System.out.println(request.getParameter("id"));

		if (modelMap.get("cart") == null) {
			Cart cart = new Cart();
			Book book = bookService.getBookById(request.getParameter("id"));
			cart.addItem(new Item(book, 1));
			modelMap.put("cart", cart);
			System.out.println("true");
		} else {
			Cart cart = (Cart) modelMap.get("cart");
			Book book = bookService.getBookById(request.getParameter("id"));
//			if(cart.getShoppingCart().contains( ))
			cart.addItem(new Item(book, 1));
			modelMap.put("cart", cart);
			System.out.println("added : " + cart.getSize());

		}
		return "redirect:/cart/view";
	}

	@RequestMapping("/view")
	public String getShoppingCart() {
		return "shopping-cart";
	}
}