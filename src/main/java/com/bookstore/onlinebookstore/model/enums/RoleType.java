package com.bookstore.onlinebookstore.model.enums;

public enum RoleType {
	ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

	private String value;

	RoleType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}