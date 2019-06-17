package com.bookstore.onlinebookstore.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Environment;

@Service
public class PaymentService {
	/* ********Braintree Payment Transaction Credentials ************ */
	private static final String MERCHANT_ID = "64fmbfx4mt6pc69j";
	private static final String PUBLIC_KEY = "cnt5rnqt5zxcmcbf";
	private static final String PRIVATE_KEY = "e533d5e2074d2bdad3e78fb988e000f6";

	/* ********Braintree Payment Transaction Credentials ************ */

	public BraintreeGateway getBrainTreeGateway() {
		return new BraintreeGateway(Environment.SANDBOX, MERCHANT_ID, PUBLIC_KEY, PRIVATE_KEY);
	}

	public Map<String, String> getClientToken() {
		BraintreeGateway gateway = getBrainTreeGateway();
		ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
		String clientToken = gateway.clientToken().generate(clientTokenRequest);
		HashMap<String, String> map = new HashMap<>();
		map.put("clientToken", clientToken);
		System.out.println("pas: " + clientToken);
		return map;
	}
}
