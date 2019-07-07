package com.bookstore.onlinebookstore.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.onlinebookstore.model.Payment;
import com.bookstore.onlinebookstore.repository.PaymentRepository;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;

@Service
public class PaymentService {
	/* ********Braintree Payment Transaction Credentials ************ */
	private static final String MERCHANT_ID = "64fmbfx4mt6pc69j";
	private static final String PUBLIC_KEY = "cnt5rnqt5zxcmcbf";
	private static final String PRIVATE_KEY = "e533d5e2074d2bdad3e78fb988e000f6";
	/* ********Braintree Payment Transaction Credentials ************ */

	@Autowired
	PaymentRepository paymentRepository;

	public BraintreeGateway getBrainTreeGateway() {
		return new BraintreeGateway(Environment.SANDBOX, MERCHANT_ID, PUBLIC_KEY, PRIVATE_KEY);
	}

	public Map<String, String> getClientToken() {
		BraintreeGateway gateway = getBrainTreeGateway();
		ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
		String clientToken = gateway.clientToken().generate(clientTokenRequest);
		HashMap<String, String> map = new HashMap<>();
		map.put("clientToken", clientToken);
		return map;
	}

	public Boolean makePayment(Long orderId, BigDecimal totalCost, String paymentMethodNonce) {
		String transactionId = processPayment(totalCost, paymentMethodNonce);
		if (transactionId != null) {
			insertPayment(orderId, transactionId);
			return true;
		}
		return false;
	}

	private void insertPayment(Long orderId, String transactionId) {
		Payment payment = new Payment();
		payment.setDateCreated(new Date());
		payment.setTransactionId(transactionId);
		payment.setOrderId(orderId);
		paymentRepository.save(payment);
	}

	public String processPayment(BigDecimal totalCost, String paymentMethodNonce) {
		Result<Transaction> result = this.processTransaction(totalCost, paymentMethodNonce);

		if (result.isSuccess()) {
			Transaction transaction = result.getTarget();
			System.out.println("Success!: " + transaction.getId());
			String transactionId = transaction.getId();
			System.out.println(transactionId);
			return transactionId;
		} else if (result.getTransaction() != null) {
			Transaction transaction = result.getTransaction();
			System.out.println("Failed!: " + transaction.getId());
			System.out.println("Error processing transaction:");
			System.out.println("  Status: " + transaction.getStatus());
			System.out.println("  Code: " + transaction.getProcessorResponseCode());
			System.out.println("  Text: " + transaction.getProcessorResponseText());
			return null;
		} else {
			for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
				System.out.println("Attribute: " + error.getAttribute());
				System.out.println("  Code: " + error.getCode());
				System.out.println("  Message: " + error.getMessage());
			}
			return null;
		}
	}

	public Result<Transaction> processTransaction(BigDecimal totalCost, String paymentMethodNonce) {
		TransactionRequest req = new TransactionRequest().amount(totalCost).paymentMethodNonce(paymentMethodNonce)
				.options().submitForSettlement(true).done();

		Result<Transaction> result = this.getBrainTreeGateway().transaction().sale(req);
		System.out.println(result.getMessage());
		return result;
	}
}
