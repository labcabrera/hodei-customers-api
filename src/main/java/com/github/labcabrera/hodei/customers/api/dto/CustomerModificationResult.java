package com.github.labcabrera.hodei.customers.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.github.labcabrera.hodei.model.commons.customer.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerModificationResult {

	@Id
	private String id;

	private String code;

	private LocalDateTime created;

	private String state;

	private List<CustomerModificationField> modifications;

	private Map<String, String> productModificationState;

	private Customer payload;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CustomerModificationField {

		private String field;

		private String before;

		private String after;

	}

}
