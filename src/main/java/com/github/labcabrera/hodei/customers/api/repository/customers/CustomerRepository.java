package com.github.labcabrera.hodei.customers.api.repository.customers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.github.labcabrera.hodei.model.commons.customer.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	//TODO revisar
	@Query("{ 'id' : ?0, 'authorization' : { '$in' : ?1} }")
	Optional<Customer> findById(String id, List<String> auth);

	Optional<Customer> findByIdCardNumber(String idCardNumber);

}