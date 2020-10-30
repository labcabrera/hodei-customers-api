package com.github.labcabrera.hodei.customers.api.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.labcabrera.hodei.customers.api.repository.commons.ProvinceRepository;
import com.github.labcabrera.hodei.model.commons.geo.Province;

@Component
public class ProvinceQueryResolver implements GraphQLQueryResolver {

	@Autowired
	private ProvinceRepository provinceRepository;

	public Province province(String id) {
		return provinceRepository.findById(id).orElse(null);
	}

}
