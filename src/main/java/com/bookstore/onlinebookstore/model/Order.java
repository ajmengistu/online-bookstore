package com.bookstore.onlinebookstore.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@Column(nullable = false, updatable = true, unique = true)
	private Long orderId;

	@Column(nullable = false, unique = true)
	private String hash;

	@Column(nullable = false, unique = true)
	private BigDecimal total;

	@Column(nullable = false, unique = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOrdered;

	@Column(nullable = false, unique = true)
	private Long addressId;

	@Column(nullable = false, unique = true)
	private Long userId;

	public Order() {
	}

	public Order(Long orderId, String hash, BigDecimal total, Date dateOrdered, Long addressId, Long userId) {
		this.orderId = orderId;
		this.hash = hash;
		this.total = total;
		this.dateOrdered = dateOrdered;
		this.addressId = addressId;
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Date getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", hash=" + hash + ", total=" + total + ", dateOrdered=" + dateOrdered
				+ ", addressId=" + addressId + ", userId=" + userId + "]";
	}
}
