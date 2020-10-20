package com.github.labcabrera.hodei.customers.api.repository.customers;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.labcabrera.hodei.customers.api.model.CustomerModificationProductConfig;

public interface CustomerModificationProductConfigRepository extends MongoRepository<CustomerModificationProductConfig, String> {

	Optional<CustomerModificationProductConfig> findByModule(String module);

}