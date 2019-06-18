package com.bookstore.onlinebookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.onlinebookstore.model.Address;
import com.bookstore.onlinebookstore.model.Item;
import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.service.AddressService;
import com.bookstore.onlinebookstore.service.OrderService;
import com.bookstore.onlinebookstore.service.OrderedBookService;

@Controller
@RequestMapping("/account")
public class CustomerController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private OrderedBookService orderedBookService;

	@RequestMapping("/order-details")
	public String getOrderDetails(@RequestParam String orderID, ModelMap modelMap) {
		if (orderID != null) {
			System.out.println(orderID);
			// if orderId is null & order-details is requested send them to order-history
			// page
			if (modelMap.get("orderId") != null) {
				// send user to order-history
			}
			System.out.println(modelMap.get("orderId"));
			Order order = orderService.getOrderByHash(orderID);
			System.out.println(order);
			modelMap.put("orderedDate", orderService.formatDate(order.getDateOrdered()));
			modelMap.put("order", order);

			Address address = addressService.getAddressById(order.getAddressId());
			modelMap.put("userAddress", address);
			List<Item> orderedBooks = orderedBookService.getOrderedBooksByOrderId(order.getOrderId());
			System.out.println(orderedBooks);
			modelMap.put("orderedBooks", orderedBooks);
			modelMap.put("totalItemsOrdered", orderedBookService.getTotalQuantity(orderedBooks));
			
			System.out.println(address);
		}
		return "order-details";
	}

	@RequestMapping("/order-history")
	public String getOrderHistory() {
		return "order-history";
	}

	@RequestMapping("/profile")
	public String getAccountProfile() {
		return "account-profile";
	}
}