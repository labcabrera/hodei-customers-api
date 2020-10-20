package com.github.labcabrera.hodei.customers.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.labcabrera.hodei.customers.api.dto.CustomerCreation;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

@Component
public class CustomerCreationConverter implements Converter<CustomerCreation, Customer<?>> {

	@Override
	public Customer<?> convert(CustomerCreation source) {
		//TODO demo: map all fields
		return Customer.builder()
			.name(source.getName())
			.surname1(source.getSurname1())
			.surname2(source.getSurname2())
			.idCard(source.getIdCard())
			.birth(source.getBirth())
			.build();
	}

}