package com.bookstore.onlinebookstore.model.enums;

public enum RoleType {
	CUSTOMER("CUSTOMER"), ADMIN("ADMIN");

	private String value;

	RoleType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}