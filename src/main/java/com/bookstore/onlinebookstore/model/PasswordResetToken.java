package com.bookstore.onlinebookstore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PasswordResetToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(targetEntity = User.class)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	private String token;

	private Date expirationDate;

	public PasswordResetToken() {
	}

	public PasswordResetToken(Long id, User user, String token, Date expirationDate) {
		this.id = id;
		this.user = user;
		this.token = token;
		this.expirationDate = expirationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "PasswordResetToken [id=" + id + ", user=" + user + ", token=" + token + ", expirationDate="
				+ expirationDate + "]";
	}
}