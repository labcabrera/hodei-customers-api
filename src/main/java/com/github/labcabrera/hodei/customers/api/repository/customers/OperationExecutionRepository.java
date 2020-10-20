package com.github.labcabrera.hodei.customers.api.repository.customers;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.labcabrera.hodei.model.commons.actions.OperationExecution;

public interface OperationExecutionRepository extends MongoRepository<OperationExecution<?>, String> {

}