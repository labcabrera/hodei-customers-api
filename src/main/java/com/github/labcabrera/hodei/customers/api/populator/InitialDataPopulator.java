package com.github.labcabrera.hodei.customers.api.populator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.labcabrera.hodei.customers.api.repository.commons.CountryRepository;
import com.github.labcabrera.hodei.customers.api.repository.commons.ProvinceRepository;
import com.github.labcabrera.hodei.customers.api.repository.customers.ProfessionRepository;
import com.github.labcabrera.hodei.model.commons.customer.Profession;
import com.github.labcabrera.hodei.model.commons.geo.Country;
import com.github.labcabrera.hodei.model.commons.geo.Province;

@Component
public class InitialDataPopulator {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private ProfessionRepository professionRepository;

	public void loadInitialData() {
		if (countryRepository.count() == 0) {
			countryRepository.insert(Country.builder()
				.id("ESP")
				.name("Spain")
				.build());
		}
		if (provinceRepository.count() == 0) {
			provinceRepository.insert(Province.builder().id("ESP-28").name("Madrid").countryId("ESP").build());
		}
		if (professionRepository.count() == 0) {
			professionRepository.insert(Profession.builder().name("Artist").build());
			professionRepository.insert(Profession.builder().name("Coder").build());
		}
	}

}
