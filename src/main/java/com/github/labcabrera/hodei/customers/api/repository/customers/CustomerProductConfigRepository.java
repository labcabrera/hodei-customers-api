package com.github.labcabrera.hodei.customers.api.repository.customers;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.github.labcabrera.hodei.customers.api.model.CustomerProductConfig;

public interface CustomerProductConfigRepository extends MongoRepository<CustomerProductConfig, String> {

	Optional<CustomerProductConfig> findByModule(String module);

	@Query("{ 'module' : ?0, 'active' : true }")
	Optional<CustomerProductConfig> findActiveByModule(String module);

}