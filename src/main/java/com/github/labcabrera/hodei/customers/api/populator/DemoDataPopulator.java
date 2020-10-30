package com.github.labcabrera.hodei.customers.api.populator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.labcabrera.hodei.customers.api.model.CustomerProductConfig;
import com.github.labcabrera.hodei.customers.api.model.CustomerProductConfig.AmqpDestination;
import com.github.labcabrera.hodei.customers.api.repository.commons.CountryRepository;
import com.github.labcabrera.hodei.customers.api.repository.commons.ProvinceRepository;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerProductConfigRepository;
import com.github.labcabrera.hodei.customers.api.repository.customers.CustomerRepository;
import com.github.labcabrera.hodei.customers.api.repository.customers.ProfessionRepository;
import com.github.labcabrera.hodei.model.commons.ContactData;
import com.github.labcabrera.hodei.model.commons.actions.OperationResult;
import com.github.labcabrera.hodei.model.commons.audit.EntityMetadata;
import com.github.labcabrera.hodei.model.commons.audit.SynchronizationInfo;
import com.github.labcabrera.hodei.model.commons.customer.CivilStatus;
import com.github.labcabrera.hodei.model.commons.customer.CommercialNotifications;
import com.github.labcabrera.hodei.model.commons.customer.Customer;
import com.github.labcabrera.hodei.model.commons.customer.CustomerProfessionInfo;
import com.github.labcabrera.hodei.model.commons.customer.Gender;
import com.github.labcabrera.hodei.model.commons.customer.IdCard;
import com.github.labcabrera.hodei.model.commons.customer.IdCardType;
import com.github.labcabrera.hodei.model.commons.customer.Profession;
import com.github.labcabrera.hodei.model.commons.geo.Address;
import com.github.labcabrera.hodei.model.commons.geo.Country;
import com.github.labcabrera.hodei.model.commons.geo.Province;
import com.github.labcabrera.hodei.model.commons.product.ProductReference;

@Component
public class DemoDataPopulator {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private ProfessionRepository professionRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerProductConfigRepository modificationProductConfigRepository;

	public OperationResult<Void> loadInitialData(boolean reset) {
		if (reset) {
			countryRepository.deleteAll();
			professionRepository.deleteAll();
			professionRepository.deleteAll();
			customerRepository.deleteAll();
			modificationProductConfigRepository.deleteAll();
		}

		if (countryRepository.count() == 0) {
			countryRepository.insert(Country.builder()
				.id("ESP")
				.name("Spain")
				.build());
			countryRepository.insert(Country.builder()
				.id("ITA")
				.name("Italy")
				.build());
		}
		if (provinceRepository.count() == 0) {
			provinceRepository.insert(Province.builder().id("ESP-28").name("Madrid").countryId("ESP").build());
		}
		if (professionRepository.count() == 0) {
			professionRepository.insert(Profession.builder().id("artits").name("Artist").build());
			professionRepository.insert(Profession.builder().id("coder").name("Coder").build());
		}
		if (modificationProductConfigRepository.count() == 0) {
			modificationProductConfigRepository.insert(CustomerProductConfig.builder()
				.module("demo-product")
				.active(true)
				.ignoredModificationStates(Arrays.asList())
				.draftStates(Arrays.asList("draft-pending-approbation"))
				.destination(AmqpDestination.builder()
					.exchange("demo-product")
					.routingKey("customer-modification")
					.build())
				.build());
		}
		if (customerRepository.count() == 0) {
			customerRepository.insert(Customer.builder()
				.name("John")
				.surname1("Doe")
				.surname2("Smith")
				.idCard(IdCard.builder()
					.number("11111111H")
					.type(IdCardType.NIF)
					.build())
				.birth(LocalDate.parse("1980-08-30"))
				.birthCountry(Country.builder()
					.id("ESP")
					.build())
				.nationalities(Arrays.asList("ESP", "ITA"))
				.gender(Gender.MALE)
				.civilStatus(CivilStatus.SINGLE)
				.fiscalAddress(Address.builder()
					.country(Country.builder().id("ESP").build())
					.province(Province.builder().id("ESP-28").build())
					.locality("Madrid")
					.zipCode("28013")
					.road("Serrano 2, 3ºA")
					.build())
				.contactData(ContactData.builder()
					.email("lab.cabrera@gmail.com")
					.build())
				.professionInfo(CustomerProfessionInfo.builder()
					.profession(Profession.builder()
						.id("artist")
						.build())
					.build())
				.productReferences(Arrays.asList(ProductReference.builder()
					.module("demo-product")
					.agreementId("demo-agreement")
					.policyId(new ObjectId().toString())
					.policyState("active")
					.build()))
				.commercialNotifications(Collections.singletonMap("demo-product", CommercialNotifications.EMAIL))
				.authorization(Arrays.asList("demo"))
				.metadata(EntityMetadata.builder()
					.created(LocalDateTime.now())
					.sync(Collections.singletonMap("demo-external-source", SynchronizationInfo.builder()
						.key(new ObjectId().toString())
						.build()))
					.build())
				.build());
		}

		return OperationResult.<Void>builder().code("200").message("Success").build();
	}

}
