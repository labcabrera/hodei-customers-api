package com.github.labcabrera.hodei.customers.api.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.github.labcabrera.hodei.model.commons.annotations.HasAuthorization;
import com.github.labcabrera.hodei.model.commons.annotations.HasId;
import com.github.labcabrera.hodei.model.commons.annotations.HasState;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "customerModifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerModificationResult implements HasId, HasAuthorization, HasState {

	@Id
	private String id;

	private String code;

	private String customerId;

	private LocalDateTime created;

	private String state;

	private List<CustomerModificationField> modifications;

	private Map<String, String> productModificationState;

	private List<String> authorization;

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
