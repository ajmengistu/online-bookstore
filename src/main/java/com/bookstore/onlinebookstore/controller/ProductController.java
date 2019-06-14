package com.bookstore.onlinebookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {
	@RequestMapping("/book")
	public String getBookDetails(@RequestParam String id, @RequestParam String title, ModelMap modelMap) {
		modelMap.put("title", "OnlineBookstore | " + title);
		return "book-details";
	}

}
