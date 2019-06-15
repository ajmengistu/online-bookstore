package com.bookstore.onlinebookstore.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<Item> shoppingCart = new ArrayList<Item>();

	public List<Item> getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(List<Item> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Integer getSize() {
		return shoppingCart.size();
	}

	public Boolean isEmpty() {
		return shoppingCart.isEmpty();
	}

	// total (bigdecimal)

	public boolean addItem(Item item) {
		return shoppingCart.add(item);
	}

	@Override
	public String toString() {
		return "Cart [shoppingCart=" + shoppingCart + "]";
	}

}
