package com.bookstore.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.onlinebookstore.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	public Address findFirstByUserIdOrderByDateAddedDesc(Long userId);	
}