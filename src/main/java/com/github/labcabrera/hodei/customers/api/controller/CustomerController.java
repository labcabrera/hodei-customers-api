package com.github.labcabrera.hodei.customers.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.labcabrera.hodei.customers.api.dto.CustomerCreation;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.customers.api.services.CustomerCreationService;
import com.github.labcabrera.hodei.model.commons.customer.Customer;
import com.github.labcabrera.hodei.rsql.service.RsqlCriteriaBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/customers", produces = "application/json")
@Tag(name = "Customers", description = "Customer operations")
@Slf4j
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerCreationService creationService;

	@Autowired
	private RsqlCriteriaBuilder criteriaBuilder;

	@Autowired
	@Qualifier("mongoTemplateCustomers")
	private MongoTemplate mongoTemplate;

	@GetMapping("/id")
	public ResponseEntity<Customer<?>> findById(String customerId) {
		log.debug("Find customer {}", customerId);
		Optional<Customer<?>> optional = customerRepository.findById(customerId);
		return ResponseEntity.of(optional);
	}

	@GetMapping
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Customer search by rsql")
	public ResponseEntity<Page<Customer>> find(
		@Parameter(example = "", required = false) @RequestParam(defaultValue = "") String rsql,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		log.debug("Search customer by rsql expression '{}'", rsql);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Criteria criteria = criteriaBuilder.build(rsql, auth, Customer.class);
		Query query = new Query(criteria);
		long total = mongoTemplate.count(new Query(criteria), Customer.class);
		List<Customer> list = mongoTemplate.find(query.with(pageable), Customer.class);
		PageImpl<Customer> result = new PageImpl<>(list, pageable, total);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Customer<?> createCustomer(@RequestBody @Valid CustomerCreation request) {
		log.debug("Received customer creation request {}", request);
		return creationService.create(request);
	}

}
