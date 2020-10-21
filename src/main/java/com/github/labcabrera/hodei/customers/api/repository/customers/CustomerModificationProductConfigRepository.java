package com.github.labcabrera.hodei.customers.api.repository.customers;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.github.labcabrera.hodei.customers.api.model.CustomerModificationProductConfig;

public interface CustomerModificationProductConfigRepository extends MongoRepository<CustomerModificationProductConfig, String> {

	@Query("{ 'module' : ?0, 'active' : true }")
	Optional<CustomerModificationProductConfig> findActiveByModule(String module);

}