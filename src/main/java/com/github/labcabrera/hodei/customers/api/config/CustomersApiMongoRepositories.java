package com.github.labcabrera.hodei.customers.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.github.labcabrera.hodei.customers.api.repository.customers", mongoTemplateRef = "mongoTemplateCustomers")
public class CustomersApiMongoRepositories {

}
