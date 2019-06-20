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

	public String resetPasswordByEmail(String email, ModelMap modelMap) {
		User user = userService.getCurrentUserByEmail(email);
		if (user != null) {
			insertPasswordResetToken(user);
//			return "redirect:/account/reset-password";
			return "redirect:/";

		} else {
			modelMap.put("ERROR",
					"Error! The provided email does not exist. Please enter an email associated with your account.");
			return "change-password";
		}

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
