package com.bookstore.onlinebookstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// A USER uses their EMAIL & password to login. Thus, here email address will be
		// used as username.
		User user = userRepository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("The provided email " + username + " does not exit!");
		}

		log.info("loadUserByUsername() : {}", username);

		return new UserDetailsImpl(user);
	}
}
