package com.github.labcabrera.hodei.customers.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.labcabrera.hodei.customers.api.populator.DemoDataPopulator;
import com.github.labcabrera.hodei.model.commons.actions.OperationResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/demo", produces = "application/json")
@Tag(name = "Demo", description = "Demo operations")
@Slf4j
public class DemoController {

	@Autowired
	private DemoDataPopulator initialDataPopulator;

	@PostMapping
	//@PreAuthorize("hasAuthority('role-system-management') or hasAuthority('root')")
	@Operation(summary = "Load demo data")
	public OperationResult<Void> loadDemoData(
		@Parameter(description = "Reset existing data") @RequestParam(name = "reset", defaultValue = "false") boolean reset) {
		log.info("Loading demo data. Reset: {}", reset);
		return initialDataPopulator.loadInitialData(reset);
	}

}
