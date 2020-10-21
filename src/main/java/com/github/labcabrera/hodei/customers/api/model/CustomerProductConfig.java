package com.github.labcabrera.hodei.customers.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "customerProductConfigurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerProductConfig {

	@Id
	private String id;

	private String module;

	private Boolean active;

	private List<String> ignoredModificationStates;

	private List<String> draftStates;

	private AmqpDestination destination;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AmqpDestination {

		private String exchange;

		private String routingKey;

	}

}
