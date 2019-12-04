package com.bookstore.onlinebookstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bookstore.onlinebookstore.model.enums.RoleType;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "user_id", nullable = false, updatable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, updatable = false, length = 50)
	private String firstName;
	@Column(nullable = false, updatable = false, length = 50)
	private String lastName;
	@Column(nullable = false, updatable = true, length = 50, unique = true)
	private String email;
	@Column(nullable = false, updatable = true, length = 255)
	private String password;
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Column(nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private RoleType userRole;

	public User() {
	}

	public User(String firstName, String lastName, String email, String password, Date dateCreated,
			RoleType userRole) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dateCreated = dateCreated;
		this.userRole = userRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public RoleType getUserRole() {
		return userRole;
	}

	public void setUserRole(RoleType userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", dateCreated=" + dateCreated + ", userRole=" + userRole + "]";
	}
}
