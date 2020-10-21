package com.github.labcabrera.hodei.customers.api.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.labcabrera.hodei.customers.api.dto.CustomerCreation;
import com.github.labcabrera.hodei.customers.api.dto.CustomerModification;
import com.github.labcabrera.hodei.customers.api.model.CustomerModificationResult;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.customers.api.service.customer.creation.CustomerCreationService;
import com.github.labcabrera.hodei.customers.api.service.customer.deletion.CustomerDeletionService;
import com.github.labcabrera.hodei.customers.api.service.customer.modification.CustomerModificationService;
import com.github.labcabrera.hodei.model.commons.actions.OperationResult;
import com.github.labcabrera.hodei.model.commons.customer.Customer;
import com.github.labcabrera.hodei.model.commons.product.ProductReference;
import com.github.labcabrera.hodei.rsql.service.RsqlService;

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
	private CustomerModificationService modificationService;

	@Autowired
	private CustomerDeletionService deletionService;

	@Autowired
	private RsqlService rsqlService;

	@Autowired
	@Qualifier("mongoTemplateCustomers")
	private MongoTemplate mongoTemplate;

	@GetMapping("/{id}")
	@Operation(summary = "Customer search by id")
	public ResponseEntity<Customer> findById(@PathVariable("id") String customerId) {
		log.debug("Find customer {}", customerId);
		Optional<Customer> optional = customerRepository.findById(customerId);
		return ResponseEntity.of(optional);
	}

	@GetMapping
	@Operation(summary = "Customer search by rsql")
	public ResponseEntity<Page<Customer>> find(
		@Parameter(example = "", required = false) @RequestParam(defaultValue = "") String rsql,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		log.debug("Search customer by rsql expression '{}'", rsql);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Page<Customer> page = rsqlService.find(rsql, pageable, auth, mongoTemplate, Customer.class);
		return ResponseEntity.ok(page);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Customer creation")
	public OperationResult<Customer> createCustomer(@RequestBody @Valid CustomerCreation request) {
		log.debug("Received customer creation request {}", request);
		return creationService.create(request);
	}

	@PatchMapping("/{id}")
	@Operation(summary = "Customer modification")
	public CustomerModificationResult createCustomer(@PathVariable("id") String customerId,
		@RequestBody @Valid CustomerModification request) {
		return modificationService.update(customerId, request);
	}

	@PutMapping("/{id}/products")
	@Operation(summary = "Updates customer product relation")
	public CustomerModificationResult createCustomer(@PathVariable("id") String customerId,
		@RequestBody @Valid ProductReference productReference) {
		return null;
	}

	@GetMapping("/{id}/modifications/{modificationId}")
	@Operation(summary = "Customer modification result", description = "Obtains the result of a customer's modification operation when the result of the operation is asynchronous")
	public CustomerModificationResult findCustomerModificationResult(
		@PathVariable("id") String customerId,
		@PathVariable("modificationId") String modificationId) {
		return null;
	}

	@GetMapping("/{id}/modifications")
	@Operation(summary = "Customer modification result")
	public Page<CustomerModificationResult> findCustomerModificationResult(
		@PathVariable("id") String customerId,
		@RequestParam(name = "rsql", defaultValue = "") String rsql,
		Pageable pageable) {
		return null;
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Customer elimination", description = "Physically eliminates a customer as long as he has no associated products")
	public OperationResult<Void> deleteCustomer(@PathVariable("id") String customerId) {
		log.debug("Received customer deletion {}", customerId);
		return deletionService.delete(customerId);
	}

}
