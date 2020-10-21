package com.github.labcabrera.hodei.customers.api.service.customer.creation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.github.labcabrera.hodei.customers.api.dto.CustomerCreation;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.customers.api.security.EntityAuthorizationResolver;
import com.github.labcabrera.hodei.customers.api.service.NotificationService;
import com.github.labcabrera.hodei.model.commons.actions.OperationResult;
import com.github.labcabrera.hodei.model.commons.audit.EntityMetadata;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

@Service
public class CustomerCreationService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private Converter<CustomerCreation, Customer> customerCreationConverter;

	@Autowired
	private EntityAuthorizationResolver entityAuthorizationResolver;

	@Autowired
	private NotificationService notificationsService;

	public OperationResult<Customer> create(CustomerCreation request) {
		Customer customer = customerCreationConverter.convert(request);
		customer.setState("created");
		customer.setMetadata(EntityMetadata.builder()
			.created(LocalDateTime.now())
			.build());
		entityAuthorizationResolver.accept(customer, SecurityContextHolder.getContext().getAuthentication());
		customerRepository.save(customer);

		notificationsService.customerCreation(customer);

		return OperationResult.<Customer>builder()
			.code("201")
			.timestamp(LocalDateTime.now())
			.message("Customer created")
			.payload(customer)
			.build();
	}
}
