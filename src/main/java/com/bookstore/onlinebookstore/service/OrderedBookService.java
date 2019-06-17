package com.bookstore.onlinebookstore.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.Item;
import com.bookstore.onlinebookstore.model.OrderedBook;
import com.bookstore.onlinebookstore.repository.OrderedBookRepository;

@Service
public class OrderedBookService {
	@Autowired
	private OrderedBookRepository orderedBookRepository;

	public void insert(Cart cart, Long orderId) {
		for (Item item : cart.getShoppingCart()) {
			OrderedBook orderedBook = new OrderedBook();
			orderedBook.setBookId(item.getBook().getId());
			orderedBook.setQuantity(item.getQuantity());
			orderedBook.setOrderId(orderId);
			orderedBook.setDateCreated(new Date());
			orderedBookRepository.save(orderedBook);
		}

	}

}
