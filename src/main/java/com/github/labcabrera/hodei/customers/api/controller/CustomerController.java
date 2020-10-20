package com.github.labcabrera.hodei.customers.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.labcabrera.hodei.customers.api.dto.CustomerCreation;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.customers.api.services.CustomerCreationService;
import com.github.labcabrera.hodei.model.commons.customer.Customer;
import com.github.labcabrera.hodei.rsql.parser.RsqlParser;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerCreationService creationService;

	@Autowired
	private RsqlParser rsqlParser;

	@GetMapping("/id")
	public ResponseEntity<Customer<?>> findById(String customerId) {
		log.debug("Find customer {}", customerId);
		Optional<Customer<?>> optional = customerRepository.findById(customerId);
		return ResponseEntity.of(optional);
	}

	@GetMapping
	public ResponseEntity<List<Customer<?>>> find(String rsql) {
		log.debug("Find customer by rsql expression '{}'", rsql);
		Query query = rsqlParser.apply(rsql, Customer.class);
		log.trace("Query: {}", query);
		//TODO security
		//TODO read from mongo template
		List<Customer<?>> list = customerRepository.findAll();
		return ResponseEntity.ok(list);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Customer<?> createCustomer(@RequestBody @Valid CustomerCreation request) {
		log.debug("Received customer creation request {}", request);
		return creationService.create(request);
	}

}
