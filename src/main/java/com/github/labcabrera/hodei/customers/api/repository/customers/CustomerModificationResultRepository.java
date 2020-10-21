package com.github.labcabrera.hodei.customers.api.repository.customers;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.labcabrera.hodei.customers.api.model.CustomerModificationResult;

public interface CustomerModificationResultRepository extends MongoRepository<CustomerModificationResult, String> {

}