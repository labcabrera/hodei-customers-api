package com.github.labcabrera.hodei.customers.api.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

@Component
public class CustomerQueryResolver implements GraphQLQueryResolver {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer customer(String id) {
		return customerRepository.findById(id).orElse(null);
	}

	public Customer customerByIdCard(String idCardNumber) {
		return customerRepository.findByIdCardNumber(idCardNumber).orElse(null);
	}

}
