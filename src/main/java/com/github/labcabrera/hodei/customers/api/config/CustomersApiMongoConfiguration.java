package com.github.labcabrera.hodei.customers.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class CustomersApiMongoConfiguration {

	@Value("${app.mongodb.commons.uri}")
	private String dbCommonsUri;

	@Value("${app.mongodb.commons.database}")
	private String dbCommonsDatabase;

	@Value("${app.mongodb.customers.uri}")
	private String dbCustomersUri;

	@Value("${app.mongodb.customers.database}")
	private String dbCustomersDatabase;

	@Bean
	MongoTemplate mongoTemplateCommons() {
		MongoClient mongoClient = MongoClients.create(dbCommonsUri);
		return new MongoTemplate(mongoClient, dbCommonsDatabase);
	}

	@Bean
	@Primary
	MongoTemplate mongoTemplateCustomers() {
		MongoClient mongoClient = MongoClients.create(dbCustomersUri);
		return new MongoTemplate(mongoClient, dbCustomersDatabase);
	}

	@Configuration
	@EnableMongoRepositories(basePackages = "com.github.labcabrera.hodei.customers.api.repository.commons", mongoTemplateRef = "mongoTemplateCommons")
	public class CommonsRepositories {

	}

	@Configuration
	@EnableMongoRepositories(basePackages = "com.github.labcabrera.hodei.customers.api.repository.customers", mongoTemplateRef = "mongoTemplateCustomers")
	public class CustomersRepositories {

	}

}
