package com.github.labcabrera.hodei.customers.api.security;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.github.labcabrera.hodei.model.commons.annotations.HasAuthorization;

@Component
public class AuthorizationFilter implements Predicate<HasAuthorization> {

	public static final String ROOT = "root";

	@Override
	public boolean test(HasAuthorization entity) {
		List<String> permissions = readAuthorities();
		if (permissions.contains(ROOT)) {
			return true;
		}
		if (entity.getAuthorization() == null || entity.getAuthorization().isEmpty()) {
			return true;
		}
		for (String i : entity.getAuthorization()) {
			if (permissions.contains(i)) {
				return true;
			}
		}
		return false;
	}

	private List<String> readAuthorities() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		if (auth == null || auth.getAuthorities() == null) {
			throw new AccessDeniedException("Invalid security context");
		}
		return auth.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList());
	}
}