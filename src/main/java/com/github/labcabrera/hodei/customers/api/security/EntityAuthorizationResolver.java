package com.github.labcabrera.hodei.customers.api.security;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.github.labcabrera.hodei.model.commons.annotations.HasAuthorization;

@Component
public class EntityAuthorizationResolver implements BiConsumer<HasAuthorization, Authentication> {

	@Override
	public void accept(HasAuthorization entity, Authentication authentication) {
		Assert.notNull(authentication, "Undefined authentication");
		Assert.notNull(authentication.getAuthorities(), "Undefined authentication authorities");
		List<String> authorities = authentication.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList());
		resolveAuthorization(entity, authentication.getName(), authorities);
	}

	private void resolveAuthorization(HasAuthorization entity, String username, List<String> authorities) {
		Assert.notNull(entity, "Undefined entity");
		Assert.isTrue(StringUtils.isNotBlank(username), "Undefined username");
		Assert.notNull(authorities, "Undefined authorities");

		if (entity.getAuthorization() == null) {
			entity.setAuthorization(new ArrayList<>());
		}
		authorities.stream().forEach(e -> {
			if (!entity.getAuthorization().contains(e) && !"root".equals(e) && !e.startsWith("role-")) {
				entity.getAuthorization().add(e);
			}
		});
	}

}
