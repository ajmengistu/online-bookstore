package com.bookstore.onlinebookstore.model.enums;

public enum RoleType {
	USER("USER"), ADMIN("ADMIN");

	private String value;

	RoleType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
