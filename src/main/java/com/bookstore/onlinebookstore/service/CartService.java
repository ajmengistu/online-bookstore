package com.bookstore.onlinebookstore.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.Item;

@Service
public class CartService {
	@Autowired
	private BookService bookService;

	public void addItemToCart(ModelMap modelMap, HttpServletRequest request) {
		if (modelMap.get("cart") == null) {
			Cart cart = new Cart();
			Book book = bookService.getBookById(request.getParameter("id"));
			cart.addItem(new Item(book, 1));
			modelMap.put("cart", cart);

		} else {
			Cart cart = (Cart) modelMap.get("cart");
			Book book = bookService.getBookById(request.getParameter("id"));
			Boolean flag = false;
			for (Item item : cart.getShoppingCart()) {
				if (item.getBook().equals(book)) {
					item.setQuantity(item.getQuantity() + 1);
					flag = true;
					break;
				}
			}

			if (!flag) {
				cart.addItem(new Item(book, 1));
				modelMap.put("cart", cart);
			}
		}

	}

	public void updateItem(ModelMap modelMap, HttpServletRequest request) {
		Cart cart = (Cart) modelMap.get("cart");
		Item itemToRemove = null;
		int qty = Integer.parseInt(request.getParameter("quantity"));

		for (Item item : cart.getShoppingCart()) {
			if (item.getBook().getId().equals(Long.parseLong(request.getParameter("id")))) {
				itemToRemove = item;
				break;
			}
		}

		if (qty == 0) {
			int i = cart.getShoppingCart().indexOf(itemToRemove);
			cart.getShoppingCart().remove(i);
		} else {
			itemToRemove.setQuantity(qty);
		}
	}
}
