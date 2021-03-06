package com.github.labcabrera.hodei.customers.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.labcabrera.hodei.customers.api.dto.CustomerCreation;
import com.github.labcabrera.hodei.model.commons.customer.Customer;
import com.github.labcabrera.hodei.model.commons.geo.Country;

@Component
public class CustomerCreationConverter implements Converter<CustomerCreation, Customer> {

	@Override
	public Customer convert(CustomerCreation source) {
		return Customer.builder()
			.name(source.getName())
			.surname1(source.getSurname1())
			.surname2(source.getSurname2())
			.idCard(source.getIdCard())
			.birth(source.getBirth())
			.birthCountry(Country.builder()
				.id(source.getBirthCountryId())
				.build())
			.nationalities(source.getNationalities())
			.fiscalAddress(source.getFiscalAddress())
			.civilStatus(source.getCivilStatus())
			.gender(source.getGender())
			.contactData(source.getContactData())
			.professionInfo(source.getProfessionInfo())
			.commercialNotifications(source.getCommercialNotifications())
			.build();
	}

}
