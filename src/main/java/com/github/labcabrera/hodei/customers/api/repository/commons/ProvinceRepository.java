package com.github.labcabrera.hodei.customers.api.repository.commons;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.labcabrera.hodei.model.commons.geo.Province;

public interface ProvinceRepository extends MongoRepository<Province, String> {

}