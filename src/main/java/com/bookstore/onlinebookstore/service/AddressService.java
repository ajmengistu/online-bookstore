package com.bookstore.onlinebookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.bookstore.onlinebookstore.model.Address;
import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.repository.AddressRepository;

@Service
public class AddressService {
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private OrderService orderService;

	public void getRecentlyUsedAddress(ModelMap modelMap, Long userId) {
		Order order = orderService.getRecentlyUsedAddress(userId);
		if (order == null) {
			Address recentlyUsedAddress = addressRepository.findByUserIdOrderBydateAddedDesc(userId);
			System.out.println(recentlyUsedAddress);
			modelMap.put("userAddress", null);
		} else {
			Address recentlyAddedAddress = addressRepository.findByUserIdOrderBydateAddedDesc(order.getAddressId());
			System.out.println(recentlyAddedAddress);
			modelMap.put("userAddress", formatAddress(recentlyAddedAddress));
		}
	}

	public Address getAddressById(Long addressId) {
		return addressRepository.findById(addressId).get();
	}

	public String formatAddress(Address address) {
		return address.getAddress1().toUpperCase() + "<br>" + address.getAddress2().toUpperCase()
				+ address.getCity().toUpperCase() + ", " + address.getState().toUpperCase() + " " + address.getZip();
	}
}
