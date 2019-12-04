package com.bookstore.onlinebookstore.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Cart {
	private List<Item> shoppingCart = new LinkedList<>();

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

	public void addItem(Item item) {
		shoppingCart.add(0, item);
	}

	public String getEstimatedDeliveryDate() {
		return LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")).toString();
	}

	public BigDecimal getTotalCost() {
		return getSubTotal().add(getShippingCost());
	}

	@Override
	public String toString() {
		return "Cart [shoppingCart=" + shoppingCart + "]";
	}
}
