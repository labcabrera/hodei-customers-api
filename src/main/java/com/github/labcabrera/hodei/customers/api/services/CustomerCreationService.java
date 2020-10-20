package com.github.labcabrera.hodei.customers.api.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.github.labcabrera.hodei.customers.api.dto.CustomerCreation;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.model.commons.audit.EntityMetadata;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

@Service
public class CustomerCreationService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private Converter<CustomerCreation, Customer<?>> customerCreationConverter;

	public Customer<?> create(CustomerCreation request) {
		Customer<?> customer = customerCreationConverter.convert(request);
		customer.setState("created");
		customer.setMetadata(EntityMetadata.builder()
			.created(LocalDateTime.now())
			.build());
		customerRepository.save(customer);
		return customer;
	}
}
