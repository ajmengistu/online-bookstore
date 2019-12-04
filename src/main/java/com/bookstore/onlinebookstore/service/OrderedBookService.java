package com.bookstore.onlinebookstore.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.Item;
import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.model.OrderedBook;
import com.bookstore.onlinebookstore.repository.OrderedBookRepository;

@Service
public class OrderedBookService {
	@Autowired
	private OrderedBookRepository orderedBookRepository;
	@Autowired
	private BookService bookService;

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

	public List<Item> getOrderedBooksByOrderId(Long orderId) {
		List<OrderedBook> orderedBooks = orderedBookRepository.findByOrderId(orderId);
		List<Item> list = new ArrayList<>();
		for (OrderedBook bookOrder : orderedBooks) {
			Book book = bookService.getBookById(bookOrder.getBookId());
			Item item = new Item(book, bookOrder.getQuantity());
			list.add(item);
		}
		return list;
	}

	public Integer getTotalQuantity(List<Item> items) {
		int total = 0;
		for (Item item : items)
			total += item.getQuantity();
		return total;
	}

	public HashMap<Order, List<Item>> getOrderedBooks(List<Order> allOrdersByUserId) {
		HashMap<Order, List<Item>> orders = new HashMap<>();
		for (Order order : allOrdersByUserId)
			orders.put(order, getOrderedBooksByOrderId(order.getOrderId()));
		return orders;
	}

}
