package com.github.labcabrera.hodei.customers.api.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.labcabrera.hodei.jwt.JwtConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Value("${app.security.jwt.secret}")
	private String jwtSecret;

	@Value("${app.demo:false}")
	private Boolean demo;

	@Bean
	public OpenAPI customOpenAPI() {
		StringBuilder description = new StringBuilder();
		description.append("<p>Customers API</p>");
		if (demo) {
			description.append("<p><h>Demo token user</h></p>");
			description.append("<pre>");
			description.append(createDemoToken("demo",
				Arrays.asList("demo", "role-extended-model", "role-customer-creation", "role-customer-modification")));
			description.append("</pre>");
			description.append("<p><h>Demo token admin</h></p>");
			description.append("<pre>");
			description.append(createDemoToken("demo-admin", Arrays.asList("demo", "role-system-manager", "role-extended-model")));
			description.append("</pre>");
		}

		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes("bearer-jwt", new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
					.in(SecurityScheme.In.HEADER)
					.description("JWT token")
					.name("Authorization")))
			.info(new Info()
				.title("Customers API")
				.description(description.toString())
				.version("1.0.0-SNAPSHOT"))
			.addSecurityItem(
				new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
	}

	private String createDemoToken(String username, List<String> roles) {
		String issuer = "hodei-customers";
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expirationDate = now.plusYears(1);
		ZoneId zoneId = ZoneId.systemDefault();
		return "Bearer " + Jwts.builder()
			.setIssuedAt(Date.from(now.atZone(zoneId).toInstant()))
			.setExpiration(Date.from(expirationDate.atZone(zoneId).toInstant())).setIssuer(issuer)
			.setSubject(username).claim(JwtConstants.KEY_CLAIM_ROLES, roles)
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

}
