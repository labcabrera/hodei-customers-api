package com.github.labcabrera.hodei.customers.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collation = "customerModificationProductConfigurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerModificationProductConfig {

	@Id
	private String id;

	private String module;

	private List<String> ignoredStates;

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
