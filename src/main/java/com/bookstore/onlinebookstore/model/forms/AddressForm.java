package com.bookstore.onlinebookstore.model.forms;

public class AddressForm {
	private String inputFirstName;
	private String inputLastName;
	private String inputAddress;
	private String inputAddress2;
	private String inputCity;
	private String inputState;
	private String inputZip;

	public AddressForm() {
	}

	public String getInputFirstName() {
		return inputFirstName;
	}

	public void setInputFirstName(String inputFirstName) {
		this.inputFirstName = inputFirstName;
	}

	public String getInputLastName() {
		return inputLastName;
	}

	public void setInputLastName(String inputLastName) {
		this.inputLastName = inputLastName;
	}

	public String getInputAddress() {
		return inputAddress;
	}

	public void setInputAddress(String inputAddress) {
		this.inputAddress = inputAddress;
	}

	public String getInputAddress2() {
		return inputAddress2;
	}

	public void setInputAddress2(String inputAddress2) {
		this.inputAddress2 = inputAddress2;
	}

	public String getInputCity() {
		return inputCity;
	}

	public void setInputCity(String inputCity) {
		this.inputCity = inputCity;
	}

	public String getInputState() {
		return inputState;
	}

	public void setInputState(String inputState) {
		this.inputState = inputState;
	}

	public String getInputZip() {
		return inputZip;
	}

	public void setInputZip(String inputZip) {
		this.inputZip = inputZip;
	}

	@Override
	public String toString() {
		return "AddressForm [inputFirstName=" + inputFirstName + ", inputLastName=" + inputLastName + ", inputAddress="
				+ inputAddress + ", inputAddress2=" + inputAddress2 + ", inputCity=" + inputCity + ", inputState="
				+ inputState + ", inputZip=" + inputZip + "]";
	}
}
