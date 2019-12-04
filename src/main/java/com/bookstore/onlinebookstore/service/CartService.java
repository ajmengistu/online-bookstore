package com.bookstore.onlinebookstore.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.Item;
import com.bookstore.onlinebookstore.model.User;

@Service
public class CartService {
	@Autowired
	private BookService bookService;
	@Autowired
	private ShoppingCartService shoppingCartService;

	public void addItemToCart(ModelMap modelMap, HttpServletRequest request) {
		if (modelMap.get("cart") == null) {
			Cart cart = new Cart();
			Book book = bookService.getBookById(request.getParameter("id"));
			Item item = new Item(book, 1);
			cart.addItem(item);
			modelMap.put("cart", cart);
			if (request.getSession().getAttribute("user") != null)
				insertItemToUserDatabase(item, (User) request.getSession().getAttribute("user"));
		} else {
			Cart cart = (Cart) modelMap.get("cart");
			Book book = bookService.getBookById(request.getParameter("id"));
			Boolean flag = false;
			for (Item item : cart.getShoppingCart()) {
				if (item.getBook().equals(book)) {
					item.setQuantity(item.getQuantity() + 1);
					flag = true;
					if (request.getSession().getAttribute("user") != null)
						updateItemToUserDatabase(item, (User) request.getSession().getAttribute("user"));
					break;
				}
			}

			if (!flag) {
				Item item = new Item(book, 1);
				cart.addItem(item);
				modelMap.put("cart", cart);
				if (request.getSession().getAttribute("user") != null)
					insertItemToUserDatabase(item, (User) request.getSession().getAttribute("user"));
			}
		}

	}

	public void updateItemToUserDatabase(Item item, User user) {
		shoppingCartService.updateItem(item, user);
	}

	public void insertItemToUserDatabase(Item item, User user) {
		shoppingCartService.insertItem(item, user);
	}

	public void deleteItemToUserDatabase(Item item, User user) {
		shoppingCartService.deleteItem(item, user);
	}

	public void updateItem(ModelMap modelMap, HttpServletRequest request) {
		Cart cart = (Cart) modelMap.get("cart");
		Item itemToUpdate = null;
		int qty = Integer.parseInt(request.getParameter("quantity"));

		for (Item item : cart.getShoppingCart()) {
			if (item.getBook().getId().equals(Long.parseLong(request.getParameter("id")))) {
				itemToUpdate = item;
				break;
			}
		}

		if (qty == 0) {
			int i = cart.getShoppingCart().indexOf(itemToUpdate);
			cart.getShoppingCart().remove(i);
			if (request.getSession().getAttribute("user") != null)
				deleteItemToUserDatabase(itemToUpdate, (User) request.getSession().getAttribute("user"));
		} else {
			itemToUpdate.setQuantity(qty);
			if (request.getSession().getAttribute("user") != null)
				updateItemToUserDatabase(itemToUpdate, (User) request.getSession().getAttribute("user"));
		}
	}
}
