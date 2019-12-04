package com.bookstore.onlinebookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookstore.onlinebookstore.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);

	@Query(value = "SELECT first_name, last_name, email, user_role from users where email = :email", nativeQuery = true)
	public User getUser(@Param("email") String email);

	public Optional<User> findById(Long id);
}