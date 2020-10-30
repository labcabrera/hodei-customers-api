package com.github.labcabrera.hodei.customers.api.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.labcabrera.hodei.customers.api.repository.customers.ProfessionRepository;
import com.github.labcabrera.hodei.model.commons.customer.Profession;

@Component
public class ProfessionQueryResolver implements GraphQLQueryResolver {

	@Autowired
	private ProfessionRepository professionRepository;

	public Profession profession(String id) {
		return professionRepository.findById(id).orElse(null);
	}

}
