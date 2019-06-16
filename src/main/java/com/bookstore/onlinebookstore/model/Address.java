package com.bookstore.onlinebookstore.model;

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
@Table(name = "addresses")
public class Address {
	@Id
	@Column(nullable = false, updatable = true, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String address1;

	@Column(nullable = true)
	private String address2;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private String zip;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAdded;

	public Address() {
	}

	public Address(String firstName, String lastName, Long userId, String address1, String address2, String city,
			String state, String zip, Date dateAdded) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.dateAdded = dateAdded;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", firstName=" + firstName + ", lastName=" + lastName + ", userId="
				+ userId + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state
				+ ", zip=" + zip + ", dateAdded=" + dateAdded + "]";
	}
}
