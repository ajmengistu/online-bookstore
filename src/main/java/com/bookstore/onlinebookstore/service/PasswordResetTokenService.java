package com.bookstore.onlinebookstore.service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.bookstore.onlinebookstore.model.PasswordResetToken;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetTokenService {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	@Autowired
	private EmailService emailService;

	public String resetPasswordByEmail(String email, ModelMap modelMap) {
		User user = userService.getCurrentUserByEmail(email);
		if (user != null) {
			insertPasswordResetToken(user);
			sendUserEmail(user);
			modelMap.put("SUCCESS",
					"Success! We have sent you an email. Please contact us if you do not receive it within a few minutes. Note: the link will expire after 10 minutes.");
			return "change-password";

		} else {
			modelMap.put("ERROR",
					"Error! The provided email does not exist. Please enter an email associated with your account.");
			return "change-password";
		}

	}

	private void sendUserEmail(User user) {
		String token = getUserGeneratedToken(user);
		String subject = "[OnlineBookstore] Password Reset E-mail";
		String body = "You're receiving this e-mail because you or someone else has requested a password reset for your user account.\r\n"
				+ "\r\n" + "Click the link below to reset your password:\r\n"
				+ "https://http://localhost:8080/account/reset?token=" + token + "\r\n" + "\r\n"
				+ "If you did not request a password reset you can safely ignore this email.\r\n" + "";
		
		emailService.sendSimpleMessage(user.getEmail(), "no-reply@onlinebookstore.com", subject, body);
	}

	private String getUserGeneratedToken(User user) {
		return passwordResetTokenRepository.findFirstByUserIdOrderByExpirationDateDesc(user.getId()).getToken();
	}

	private void insertPasswordResetToken(User user) {
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUser(user);
		passwordResetToken.setExpirationDate(getExirationDate());
		passwordResetToken.setToken(getGeneratedToken());
		passwordResetTokenRepository.save(passwordResetToken);
	}

	private String getGeneratedToken() {
		return UUID.randomUUID().toString();
	}

	public Date getExirationDate() {
		final long ONE_MINUTE_IN_MILLIS = 60000; // milliseconds

		Calendar date = Calendar.getInstance();
		long t = date.getTimeInMillis();
		Date afterAddingTenMins = new Date(t + (10 * ONE_MINUTE_IN_MILLIS));
		return afterAddingTenMins;
	}

}
