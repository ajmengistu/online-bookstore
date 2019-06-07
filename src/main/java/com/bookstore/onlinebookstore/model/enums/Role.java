package com.bookstore.onlinebookstore.model.enums;

public enum Role {
	USER("USER"), ADMIN("ADMIN");

	private String value;

	Role(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
