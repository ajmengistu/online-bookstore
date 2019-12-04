package com.bookstore.onlinebookstore.service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

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
		String WEB_HOST = "localhost:8080";
		String token = getUserGeneratedToken(user);
		String subject = "[OnlineBookstore] Password Reset E-mail";
		String body = "You're receiving this e-mail because you or someone else has requested a password reset for your user account.\r\n"
				+ "\r\n" + "Click the link below to reset your password:\r\n" + "http://" + WEB_HOST
				+ "/account/password/reset/?token=" + token + "\r\n" + "\r\n"
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

	public PasswordResetToken getPasswordResetTokenByToken(String token) {
		return passwordResetTokenRepository.findFirstByToken(token);
	}

	public void verifyToken(String token, ModelMap modelMap) {
		PasswordResetToken passwordResetToken = getPasswordResetTokenByToken(token);
		if (passwordResetToken != null && new Date().before(passwordResetToken.getExpirationDate())) {
			modelMap.put("TOKEN_VALID", "TOKEN_VALID");
		} else {
			modelMap.put("TOKEN_ERROR", "The requested link is invalid or has expired.");
		}
	}

	public String updatePassword(ModelMap modelMap, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String newPassword = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		if (newPassword.equals(confirmPassword)) {
			PasswordResetToken passwordResetToken = getPasswordResetTokenByToken(request.getParameter("token"));
			passwordResetToken.getUser().setPassword(passwordEncoder.encode(newPassword));
			passwordResetTokenRepository.save(passwordResetToken);

			redirectAttributes.addFlashAttribute("PASSWORD_RESET_SUCCESSFUL",
					"Success! Your password has been reset successfully.");
			return "redirect:/login";
		}
		modelMap.put("PASSWORD_MATCH_ERROR", "Error! Your password does not match. Please try again.");
		return "reset-token";
	}
}
