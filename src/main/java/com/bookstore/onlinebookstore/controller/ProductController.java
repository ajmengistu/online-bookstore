package com.bookstore.onlinebookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.service.BookService;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private BookService bookService;

	@RequestMapping("/book")
	public String getBookDetails(@RequestParam String id, @RequestParam String title, ModelMap modelMap) {
		modelMap.put("title", "OnlineBookstore | " + title);
		Book book = bookService.getBookById(id);
		modelMap.put("book", book);
		modelMap.put("similarBooks", bookService.getBooksByAuthor(book));
		return "book-details";
	}
}
