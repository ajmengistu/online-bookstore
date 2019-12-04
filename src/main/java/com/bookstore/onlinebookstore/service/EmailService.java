package com.bookstore.onlinebookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.bookstore.onlinebookstore.config.EmailConfiguration;

@Service
public class EmailService {
	@Autowired
	private EmailConfiguration emailConfiguration;

	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost(emailConfiguration.getHost());
		mailSenderImpl.setPort(emailConfiguration.getPort());
		mailSenderImpl.setUsername(emailConfiguration.getUsername());
		mailSenderImpl.setPassword(emailConfiguration.getPassword());
		return mailSenderImpl;
	}

	public void sendSimpleMessage(String to, String from, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		message.setFrom(from);
		getJavaMailSender().send(message);
	}
}
