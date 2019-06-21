package com.bookstore.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.onlinebookstore.model.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	public PasswordResetToken findFirstByUserIdOrderByExpirationDateDesc(Long id);

	public PasswordResetToken findFirstByToken(String token);
}
