package com.github.labcabrera.hodei.customers.api.graphql;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomerQueryResolver implements GraphQLQueryResolver {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer customer(String id) {
		//TODO meter todo esto a nivel de un servicio
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<String> grants = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.filter(e -> !e.startsWith("role-"))
				.collect(Collectors.toList());
			log.trace("Grants: {}", grants);
		}
		//TODO integrar query con seguridad
		return customerRepository.findById(id).orElse(null);
	}

	public Customer customerByIdCard(String idCardNumber) {
		return customerRepository.findByIdCardNumber(idCardNumber).orElse(null);
	}

}
