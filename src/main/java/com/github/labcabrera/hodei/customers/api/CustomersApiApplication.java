package com.github.labcabrera.hodei.customers.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomersApiApplication.class, args);
	}

}
