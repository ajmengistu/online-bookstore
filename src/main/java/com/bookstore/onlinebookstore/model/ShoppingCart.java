package com.bookstore.onlinebookstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(ShoppingCartId.class)
@Table(name = "carts")
public class ShoppingCart {
	@Id
	@Column(updatable = true, nullable = false)
	private Long userId;
	@Id
	@Column(updatable = true, nullable = false)
	private Long bookId;
	@Column(updatable = true, nullable = false)
	private Integer quantity;
	@Column(updatable = true, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public ShoppingCart() {
	}

	public ShoppingCart(Long userId, Long bookId, Integer quantity, Date dateCreated) {
		this.userId = userId;
		this.bookId = bookId;
		this.quantity = quantity;
		this.dateCreated = dateCreated;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
