package com.bookstore.onlinebookstore.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
		order.setHash(generateOrderNumberHash());
		orderRepository.save(order);
	}

	public Long placeOrder(ModelMap modelMap, Long userId, BigDecimal totalCost, Long addressId) {
		insertOrder(modelMap, userId, totalCost, addressId);
		return orderRepository.findFirstByOrderByOrderIdDesc().getOrderId();
	}

	public String generateOrderNumberHash() {
		return UUID.randomUUID().toString();
	}

	public void updatePayed(Long orderId) {
		Order order = orderRepository.findById(orderId).get();
		order.setPayed(true);
		orderRepository.save(order);
	}

	public Order getOrderById(Long orderId) {
		return orderRepository.findById(orderId).get();

	}

	public String getOrderHash(Long orderId) {
		return getOrderById(orderId).getHash();
	}

	public String formatDate(Date date) {
		DateFormat df = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
		return df.format(date).toString();

	}

	public Order getOrderByHash(String orderID) {
		return orderRepository.findFirstByHash(orderID);

	}

	public Order getOrderByHashAndByUserId(String hash, Long userId) {
		return orderRepository.findFirstByHashAndUserId(hash, userId);
	}
	
	public List<Order> getAllOrdersByUserId(Long userId){
		return orderRepository.findAllByUserId(userId);
	}
}