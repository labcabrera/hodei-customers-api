package com.github.labcabrera.hodei.customers.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.labcabrera.hodei.customers.api.repository.customers.ProfessionRepository;
import com.github.labcabrera.hodei.model.commons.customer.Profession;
import com.github.labcabrera.hodei.rsql.service.RsqlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/professions", produces = "application/json")
@Tag(name = "Professions")
@Slf4j
public class ProfessionController {

	@Autowired
	private ProfessionRepository customerRepository;

	@Autowired
	private RsqlService rsqlService;

	@Autowired
	@Qualifier("mongoTemplateCustomers")
	private MongoTemplate mongoTemplate;

	@GetMapping("/id")
	@Operation(summary = "Profesion search by id")
	public ResponseEntity<Profession> findById(String professionId) {
		log.debug("Find customer {}", professionId);
		Optional<Profession> optional = customerRepository.findById(professionId);
		return ResponseEntity.of(optional);
	}

	@GetMapping
	@Operation(summary = "Profesion search by rsql")
	public ResponseEntity<Page<Profession>> find(
		@Parameter(example = "", required = false) @RequestParam(defaultValue = "") String rsql,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		log.debug("Search customer by rsql expression '{}'", rsql);
		Page<Profession> page = rsqlService.find(rsql, pageable, mongoTemplate, Profession.class);
		return ResponseEntity.ok(page);
	}

}
