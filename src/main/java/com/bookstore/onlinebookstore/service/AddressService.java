package com.bookstore.onlinebookstore.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.bookstore.onlinebookstore.model.Address;
import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.model.forms.AddressForm;
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
			Address recentlyUsedAddress = addressRepository.findFirstByUserIdOrderByDateAddedDesc(userId);
			System.out.println(recentlyUsedAddress);
			if (recentlyUsedAddress != null)
				modelMap.put("userAddress", formatAddress(recentlyUsedAddress));
			else
				modelMap.put("userAddress", null);
		} else {
			Address recentlyAddedAddress = addressRepository.findById(order.getAddressId()).get();
			
			modelMap.put("userAddress", formatAddress(recentlyAddedAddress));
		}
	}

	public Address getAddressById(Long addressId) {
		return addressRepository.findById(addressId).get();
	}

	public Address formatAddress(Address address) {		
		address.setAddress1(address.getAddress1().toUpperCase());
		if (address.getAddress2() != null) {
			address.setAddress2(address.getAddress2().toUpperCase());
		}
		address.setCity(address.getCity().toUpperCase());
		address.setState(address.getState().toUpperCase());
		address.setDateAdded(null);
		address.setUserId(null);
//		address.setAddressId(null);
		return address;
	}

	public void addAddress(AddressForm addressForm, Long id) {
		Address address = new Address();
		address.setUserId(id);
		address.setFirstName(addressForm.getInputFirstName());
		address.setLastName(addressForm.getInputLastName());
		address.setAddress1(addressForm.getInputAddress());
		address.setAddress2(addressForm.getInputAddress2());
		address.setCity(addressForm.getInputCity());
		address.setState(addressForm.getInputState());
		address.setZip(addressForm.getInputZip());
		address.setDateAdded(new Date());
		addressRepository.save(address);
	}

	public void updateAddress(AddressForm addressForm, Long id) {
		addAddress(addressForm, id);

	}
}
