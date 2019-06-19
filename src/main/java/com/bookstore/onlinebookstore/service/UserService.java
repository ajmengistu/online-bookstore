package com.bookstore.onlinebookstore.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void updateUserAccountPassword(HttpServletRequest request, RedirectAttributes redirectAttr, String email) {
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		if (!newPassword.equals(confirmPassword)) {
			redirectAttr.addFlashAttribute("ERROR", "Error! Your password did not match. Please try again.");
		} else {
			if (isCurrentPasswordValid(currentPassword, email)) {
				updateUserPassword(newPassword, email);
				redirectAttr.addFlashAttribute("SUCCESS", "Success! Your password has been updated.");
			} else {
				redirectAttr.addFlashAttribute("ERROR", "Error! Your current password is invalid. Please try again.");
			}
		}
	}

	private void updateUserPassword(String newPassword, String email) {
		User user = getCurrentUserByEmail(email);
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	public boolean isCurrentPasswordValid(String password, String email) {
		System.out.println(passwordEncoder.matches(password, getPasswordByEmail(email)));
		if (passwordEncoder.matches(password, getPasswordByEmail(email))) {
			return true;
		} else {
			return false;
		}
	}

	public String getPasswordByEmail(String email) {
		return userRepository.findByEmail(email).getPassword();
	}

	public User getCurrentUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void updateUserAccountEmail(HttpServletRequest request, RedirectAttributes redirectAttr, String email) {
		String currentEmail = request.getParameter("currentEmail");
		String newEmail = request.getParameter("newEmail");
		String confirmEmail = request.getParameter("confirmEmail");

		if (!newEmail.equals(confirmEmail)) {
			redirectAttr.addFlashAttribute("ERROR", "Error! Your email did not match. Please try again.");
		} else {
			if (currentEmail.equals(email)) {
				if (updateUserEmail(newEmail, currentEmail)) {
					redirectAttr.addFlashAttribute("SUCCESS", "Success! Your email has been updated.");
					redirectAttr.addFlashAttribute("newEmail", newEmail);
				} else {
					redirectAttr.addFlashAttribute("ERROR",
							"Error! The provided new email already exists. Please choose a different email.");
				}
			} else {
				redirectAttr.addFlashAttribute("ERROR", "Error! The provided email does not exist. Please try again.");
			}
		}

	}

	public boolean updateUserEmail(String newEmail, String currentEmail) {
		User user = getCurrentUserByEmail(newEmail);
		if (user == null) {
			User user2 = getCurrentUserByEmail(currentEmail);
			user2.setEmail(newEmail);
			userRepository.save(user2);
			return true;
		} else {
			return false;
		}

	}
}
