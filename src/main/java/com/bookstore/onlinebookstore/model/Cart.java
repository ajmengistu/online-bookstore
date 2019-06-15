package com.bookstore.onlinebookstore.model;

import java.math.BigDecimal;
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

	public BigDecimal getShippingCost() {
		return new BigDecimal("5.99");
	}

	public Integer getSize() {
		int totalItems = 0;
		for (Item item : shoppingCart)
			totalItems += item.getQuantity();
		return totalItems;
	}

	public Boolean isEmpty() {
		return shoppingCart.isEmpty();
	}

	public BigDecimal getSubTotal() {
		BigDecimal total = new BigDecimal("0");
		for (Item item : shoppingCart)
			total = total.add(item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity().toString())));
		return total;
	}

	public boolean addItem(Item item) {
		return shoppingCart.add(item);
	}
	
	public BigDecimal getTotalCost() {
		return getSubTotal().add(getShippingCost());
	}

	@Override
	public String toString() {
		return "Cart [shoppingCart=" + shoppingCart + "]";
	}

}
