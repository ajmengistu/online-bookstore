package com.bookstore.onlinebookstore.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	public Order getRecentlyUsedAddress(Long userId) {
		return orderRepository.findFirstByUserIdOrderByDateOrderedDesc(userId);
	}

	public void insertOrder(ModelMap modelMap, Long userId, BigDecimal totalCost, Long addressId) {
		Order order = new Order();
		order.setAddressId(addressId);
		order.setDateOrdered(new Date());
		order.setUserId(userId);
		order.setTotal(totalCost);
		order.setHash(getOrderNumberHash());
		orderRepository.save(order);
	}

	public Long placeOrder(ModelMap modelMap, Long userId, BigDecimal totalCost, Long addressId) {
		insertOrder(modelMap, userId, totalCost, addressId);
		return orderRepository.findFirstByOrderByOrderIdDesc().getOrderId();
	}

	public String getOrderNumberHash() {
		return UUID.randomUUID().toString();
	}

	public void updatePayed(Long orderId) {
		Order order = orderRepository.findById(orderId).get();
		order.setPayed(true);
		orderRepository.save(order);
	}

}