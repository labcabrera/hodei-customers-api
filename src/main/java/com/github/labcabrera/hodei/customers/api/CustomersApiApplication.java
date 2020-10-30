package com.github.labcabrera.hodei.customers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.github.labcabrera.hodei.customers.api.populator.DemoDataPopulator;

@SpringBootApplication
@EnableCaching
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomersApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CustomersApiApplication.class, args);
	}

	@Autowired
	private DemoDataPopulator demoDataPopulator;

	@Override
	public void run(String... args) throws Exception {
		demoDataPopulator.loadInitialData(false);
	}

}
