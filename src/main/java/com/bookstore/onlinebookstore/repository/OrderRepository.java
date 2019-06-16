package com.bookstore.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.onlinebookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	public Order findByUserIdOrderBydateOrderedDesc(Long userId);
}
