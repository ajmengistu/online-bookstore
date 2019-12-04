package com.bookstore.onlinebookstore.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@Column(nullable = false, updatable = true, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(nullable = false)
	private String hash;

	@Column(nullable = false)
	private BigDecimal total;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOrdered;

	@Column(nullable = false)
	private Long addressId;

	@Column(nullable = false)
	private Long userId;


	@Column(nullable = false, updatable = true, columnDefinition = "boolean default false")
	private Boolean payed = false;

	public Order() {
	}

	public Order(Long orderId, String hash, BigDecimal total, Date dateOrdered, Long addressId, Long userId,
			Boolean payed) {
		this.orderId = orderId;
		this.hash = hash;
		this.total = total;
		this.dateOrdered = dateOrdered;
		this.addressId = addressId;
		this.userId = userId;
		this.payed = payed;
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

	public Boolean getPayed() {
		return payed;
	}

	public void setPayed(Boolean payed) {
		this.payed = payed;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", hash=" + hash + ", total=" + total + ", dateOrdered=" + dateOrdered
				+ ", addressId=" + addressId + ", userId=" + userId + ", payed=" + payed + "]";
	}
}
