package com.bookstore.onlinebookstore.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.onlinebookstore.model.Item;
import com.bookstore.onlinebookstore.model.ShoppingCart;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	public void insertItem(Item item, User user) {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUserId(user.getId());
		shoppingCart.setBookId(item.getBook().getId());
		shoppingCart.setDateCreated(new Date());
		shoppingCart.setQuantity(item.getQuantity());
		shoppingCartRepository.save(shoppingCart);
	}

	public void updateItem(Item item, User user) {
		ShoppingCart sc = shoppingCartRepository.findByUserIdAndBookId(user.getId(), item.getBook().getId());
		sc.setQuantity(item.getQuantity());
		shoppingCartRepository.save(sc);
	}
	
	public void deleteItem(Item item, User user) {
		ShoppingCart sc = shoppingCartRepository.findByUserIdAndBookId(user.getId(), item.getBook().getId());
		shoppingCartRepository.delete(sc);
	}
}
