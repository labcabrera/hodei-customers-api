package com.github.labcabrera.hodei.customers.api.service.customer.deletion;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.github.labcabrera.hodei.model.commons.actions.OperationResult;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

@Service
public class CustomerDeletionService {

	public OperationResult<Customer> delete(String id) {
		return OperationResult.<Customer>builder()
			.code("501")
			.timestamp(LocalDateTime.now())
			.message("Not implemented")
			.build();
	}
}
