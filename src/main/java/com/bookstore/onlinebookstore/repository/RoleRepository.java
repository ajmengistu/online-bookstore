package com.bookstore.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.onlinebookstore.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
