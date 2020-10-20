package com.github.labcabrera.hodei.customers.api.repository.commons;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.labcabrera.hodei.model.commons.geo.Country;

public interface CountryRepository extends MongoRepository<Country, String> {

}