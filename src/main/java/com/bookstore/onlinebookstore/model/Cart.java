package com.bookstore.onlinebookstore.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<Item> shoppingCart = new ArrayList<Item>();

	public Integer getSize() {
		return shoppingCart.size();
	}

	// total (bigdecimal)
	//
}
