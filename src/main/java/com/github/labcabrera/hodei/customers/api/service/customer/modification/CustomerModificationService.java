package com.github.labcabrera.hodei.customers.api.service.customer.modification;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.github.labcabrera.hodei.customers.api.dto.CustomerModification;
import com.github.labcabrera.hodei.customers.api.model.CustomerModificationResult;
import com.github.labcabrera.hodei.customers.api.model.CustomerModificationResult.CustomerModificationField;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerModificationResultRepository;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.customers.api.security.AuthorizationFilter;
import com.github.labcabrera.hodei.customers.api.security.EntityAuthorizationResolver;
import com.github.labcabrera.hodei.customers.api.service.NotificationService;
import com.github.labcabrera.hodei.model.commons.customer.Customer;
import com.github.labcabrera.hodei.model.commons.exception.EntityNotFoundException;

@Service
public class CustomerModificationService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AuthorizationFilter authorizationFilter;

	@Autowired
	private CustomerFieldModificationService customerFieldModificationService;

	@Autowired
	private CustomerProductService customerProductService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CustomerModificationResultRepository customerModificationResultRepository;

	@Autowired
	private EntityAuthorizationResolver entityAuthorizationResolver;

	public CustomerModificationResult update(String customerId, @Valid CustomerModification request) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		if (!authorizationFilter.test(customer)) {
			throw new AccessDeniedException("Forbiden");
		}

		List<CustomerModificationField> list = customerFieldModificationService.merge(customer, request);
		if (list.isEmpty()) {
			return CustomerModificationResult.builder().code("304").build();
		}

		customer.getMetadata().setUpdated(LocalDateTime.now());
		customerRepository.save(customer);

		notificationService.customerModification(customer);

		CustomerModificationResult result = CustomerModificationResult.builder()
			.created(LocalDateTime.now())
			.state("pending")
			.payload(customer)
			.customerId(customerId)
			.productModificationState(new LinkedHashMap<>())
			.modifications(list)
			.build();

		customerProductService.process(customer, result);

		entityAuthorizationResolver.accept(result, SecurityContextHolder.getContext().getAuthentication());
		customerModificationResultRepository.insert(result);
		return result;
	}

}
