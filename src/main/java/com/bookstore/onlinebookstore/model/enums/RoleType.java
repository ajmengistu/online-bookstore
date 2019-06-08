package com.bookstore.onlinebookstore.model.enums;

public enum RoleType {
	ROLE_CUSTOMER("ROLE_CUSTOMER"), ROLE_ADMIN("ROLE_ADMIN");

	private String value;

	RoleType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}