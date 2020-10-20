package com.github.labcabrera.hodei.customers.api.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes("bearer-jwt", new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
					.in(SecurityScheme.In.HEADER)
//					.description("JWT token")
					.name("Authorization")))
			.info(new Info()
				.title("Customers API")
//				.description(
//					"Demo token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2MDMyMjA4OTcsImV4cCI6NDc1ODg5NDQ5NywiaXNzIjoiaG9kZWkiLCJzdWIiOiJyb290IiwiYXBwUm9sZXMiOlsiaG9kZWktc2FtcGxlLWNsaWVudCxyb2xlLWN1c3RvbWVyLWNyZWF0aW9uLHJvbGUtZXh0ZW5kZWQtbW9kZWwiXX0.5ncF0hXbiQsXSgGBB1CXvgo4KeyRsqBBShDBR77r_fTmDWp2mdcVv_qHzBq_-vTRkylqVWhBC27T4SvO6j639g")
				.version("1.0.0-SNAPSHOT"))
			.addSecurityItem(
				new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
	}
}
