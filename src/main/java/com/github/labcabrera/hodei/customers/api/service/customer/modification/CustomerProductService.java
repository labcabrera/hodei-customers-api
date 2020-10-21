package com.github.labcabrera.hodei.customers.api.service.customer.modification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.labcabrera.hodei.customers.api.dto.CustomerModificationResult;
import com.github.labcabrera.hodei.customers.api.model.CustomerModificationProductConfig.AmqpDestination;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerModificationProductConfigRepository;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerProductService {

	@Autowired
	private CustomerModificationProductConfigRepository configRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public CustomerModificationResult process(Customer customer, CustomerModificationResult result) {
		if (customer.getProductReferences() == null || customer.getProductReferences().isEmpty()) {
			log.debug("Customer has not products");
			result.setState("completed");
			return result;
		}

		List<AmqpDestination> destinations = new ArrayList<>();

		customer.getProductReferences().stream().forEach(e -> {
			String module = e.getModule();
			String state = e.getPolicyState();
			configRepository.findActiveByModule(module).ifPresent(config -> {
				if (!config.getIgnoredStates().contains(state)) {
					result.getProductModificationState().put(module, "pending");
					destinations.add(config.getDestination());
				}
			});
		});

		destinations.forEach(e -> sendMessage(result, e));
		return result;
	}

	private void sendMessage(CustomerModificationResult result, AmqpDestination destination) {
		log.debug("Sending AMQP customer modification message {}:{}", destination.getExchange(), destination.getRoutingKey());
		String message = createMessage(result);
		String exchange = destination.getExchange();
		String routingKey = destination.getRoutingKey();
		rabbitTemplate.convertAndSend(exchange, routingKey, message, m -> {
			m.getMessageProperties().getHeaders().put("content_type", "application/json");
			return m;
		});
	}

	private String createMessage(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		}
		catch (JsonProcessingException ex) {
			throw new RuntimeException("Error creating callback message");
		}
	}

}
