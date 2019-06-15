package com.bookstore.onlinebookstore.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.bookstore.onlinebookstore.model.enums.RoleType;

@Configuration
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String targetUrl = determineTargetUrl(authentication, request.getSession().getAttribute("URL_REF").toString());
		if (response.isCommitted()) {

		}
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(Authentication authentication, String url_ref) {
		String url = "/login?error=true";

		// Fetch the roles from Authentication object
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority auth : authorities) {
			roles.add(auth.getAuthority());
		}

		// check user role and decide the redirect URL
		if (roles.contains(RoleType.ADMIN.toString())) {
			// url = /loginSuccessful?role=admin
			url = "/admin";
		} else if (roles.contains(RoleType.CUSTOMER.toString())) {
			// url = /loginSuccessful?role=customer
			if (url_ref != null && url_ref.contains("/cart/view")) {
				System.out.println("-------------------------------" + url_ref);
				url = "/cart/checkout";
			} else {
				System.out.println("hello: " + url_ref);
				url = "/account";
			}
		}
		return url;
	}
}
