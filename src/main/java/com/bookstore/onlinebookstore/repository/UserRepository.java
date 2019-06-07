package com.bookstore.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.onlinebookstore.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
