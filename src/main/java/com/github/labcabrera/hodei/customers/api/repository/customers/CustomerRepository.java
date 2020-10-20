package com.github.labcabrera.hodei.customers.api.repository.customers;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.labcabrera.hodei.model.commons.customer.Customer;

public interface CustomerRepository extends MongoRepository<Customer<?>, String> {

	Optional<Customer<?>> findByIdCardNumber(String idCardNumber);

}