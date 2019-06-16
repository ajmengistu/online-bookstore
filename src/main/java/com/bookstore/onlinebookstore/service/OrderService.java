package com.bookstore.onlinebookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	public Order getRecentlyUsedAddress(Long userId) {
		return orderRepository.findByUserIdOrderBydateOrderedDesc(userId);
	}
}
