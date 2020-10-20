package com.github.labcabrera.hodei.customers.api.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.github.labcabrera.hodei.model.commons.ContactData;
import com.github.labcabrera.hodei.model.commons.customer.CivilStatus;
import com.github.labcabrera.hodei.model.commons.customer.CustomerProfessionInfo;
import com.github.labcabrera.hodei.model.commons.customer.Gender;
import com.github.labcabrera.hodei.model.commons.customer.IdCard;
import com.github.labcabrera.hodei.model.commons.geo.Address;
import com.github.labcabrera.hodei.model.commons.validation.annotation.ExistingCountry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCreation {

	@Valid
	@NotNull(message = "{validation.constraints.abstract-entity.required-id-card}")
	@Schema(description = "Indicates the identification document", required = true)
	private IdCard idCard;

	@NotBlank(message = "{validation.constraints.person.expected-name}")
	@Schema(description = "Name", required = true, example = "John")
	private String name;

	@NotBlank(message = "{validation.constraints.person.expected-surname1}")
	@Schema(description = "First surname", required = true, example = "Doe")
	private String surname1;

	@Schema(description = "Second surname", required = false, example = "Smith")
	private String surname2;

	@NotNull(message = "{validation.constraints.person.expected-birth-date}")
	@Past
	@Schema(description = "Birth date", required = true, example = "1977-11-03")
	private LocalDate birth;

	@ExistingCountry
	@Schema(description = "Birth country for this persons", required = true, example = "ESP")
	private String birthCountryId;

	@ExistingCountry
	@Schema(description = "Nationalities for this person. It is a list because a person can have several")
	private List<String> nationalities;

	@Valid
	@NotNull(message = "{validation.constraints.abstract-entity.required-fiscal-address}")
	@Schema(description = "Fiscal address for this person. It is unique accross the policies", required = true)
	private Address fiscalAddress;

	@NotNull(message = "{validation.constraints.person.expected-civil-status}")
	@Schema(description = "Civil status", required = false, example = "single")
	private CivilStatus civilStatus;

	@NotNull(message = "{validation.constraints.person.expected-gender}")
	@Schema(description = "Gender", required = true, example = "male")
	private Gender gender;

	@Valid
	@NotNull(message = "{validation.constraints.abstract-entity.required-contact-data}")
	@Schema(description = "Contact data. Phones, fax, emails and websites", required = true)
	private ContactData contactData;

	@Valid
	@Schema(description = "Profession information")
	private CustomerProfessionInfo professionInfo;

}
