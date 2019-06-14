package com.bookstore.onlinebookstore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.service.BookService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private BookService bookService;

	@PostMapping("/cart.do")
	public String cartItem(RedirectAttributes redirectAttr, ModelMap modelMap, HttpServletRequest request) {
//		redirectAttr.addAttribute(attributeValue)
		System.out.println(request.getParameter("id"));
		
		if (modelMap.get("shoppingCart") == null) {
			modelMap.addAttribute("shoppingCart", new ArrayList<Book>());
		} else {
			@SuppressWarnings({ "unchecked", "unused" })
			List<Book> cart = (List<Book>) modelMap.get("shoppingCart");
			Book book = bookService.getBookById(request.getParameter("id"));
		}
		return "redirect:/cart/view";
	}

	@RequestMapping("/view")
	public String getShoppingCart() {
		return "shopping-cart";
	}
}