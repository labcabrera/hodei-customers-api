package com.github.labcabrera.hodei.customers.api.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.labcabrera.hodei.customers.api.repository.commons.CountryRepository;
import com.github.labcabrera.hodei.model.commons.geo.Country;

@Component
public class CountryQueryResolver implements GraphQLQueryResolver {

	@Autowired
	private CountryRepository countryRepository;

	public Country country(String id) {
		return countryRepository.findById(id).orElse(null);
	}

}
