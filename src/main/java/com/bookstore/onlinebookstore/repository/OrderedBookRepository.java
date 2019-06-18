package com.bookstore.onlinebookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.onlinebookstore.model.OrderedBook;

@Repository
public interface OrderedBookRepository extends JpaRepository<OrderedBook, Long> {
	public List<OrderedBook> findByOrderId(Long orderId);
}
