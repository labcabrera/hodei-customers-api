package com.github.labcabrera.hodei.customers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.github.labcabrera.hodei.customers.api.populator.InitialDataPopulator;

@SpringBootApplication
@EnableCaching
public class CustomersApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CustomersApiApplication.class, args);
	}

	@Autowired
	private InitialDataPopulator initialDataPopulator;

	@Override
	public void run(String... args) throws Exception {
		initialDataPopulator.loadInitialData();
	}

}
