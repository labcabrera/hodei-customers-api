package com.github.labcabrera.hodei.customers.api.service.customer.deletion;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerProductConfigRepository;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.customers.api.security.AuthorizationFilter;
import com.github.labcabrera.hodei.model.commons.actions.OperationResult;
import com.github.labcabrera.hodei.model.commons.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerDeletionService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerProductConfigRepository customerProductConfigRepository;

	@Autowired
	private AuthorizationFilter authFilter;

	public OperationResult<Void> delete(String id) {
		customerRepository.findById(id).ifPresentOrElse(customer -> {
			log.debug("Processing customer elimination {}", customer.getId());
			if (!authFilter.test(customer)) {
				throw new AccessDeniedException("Forbiden");
			}
			customer.getProductReferences().forEach(reference -> {
				String module = reference.getModule();
				customerProductConfigRepository.findByModule(module).ifPresent(config -> {
					String state = reference.getPolicyState();
					if (!config.getDraftStates().contains(state)) {
						throw new ConstraintViolationException("Customer has product references", null);
					}
				});
			});
			customerRepository.delete(customer);
		}, () -> {
			throw new EntityNotFoundException("Not found");
		});
		return OperationResult.<Void>builder()
			.code("200")
			.timestamp(LocalDateTime.now())
			.message("Success")
			.build();
	}

}
