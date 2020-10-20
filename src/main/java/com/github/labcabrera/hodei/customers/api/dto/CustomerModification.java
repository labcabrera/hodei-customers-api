package com.github.labcabrera.hodei.customers.api.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
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
public class CustomerModification {

	@Valid
	@Schema(description = "Indicates the identification document")
	private IdCard idCard;

	@Schema(description = "Name", example = "John")
	private String name;

	@Schema(description = "First surname", example = "Doe")
	private String surname1;

	@Schema(description = "Second surname", example = "Smith")
	private String surname2;

	@Past
	@Schema(description = "Birth date", example = "1977-11-03")
	private LocalDate birth;

	@ExistingCountry
	@Schema(description = "Birth country for this persons", example = "ESP")
	private String birthCountryId;

	@ExistingCountry
	@Schema(description = "Nationalities for this person. It is a list because a person can have several")
	private List<String> nationalities;

	@Valid
	@Schema(description = "Fiscal address for this person. It is unique accross the policies")
	private Address fiscalAddress;

	@Schema(description = "Civil status", example = "single")
	private CivilStatus civilStatus;

	@Schema(description = "Gender", example = "male")
	private Gender gender;

	@Valid
	@Schema(description = "Contact data. Phones, fax, emails and websites")
	private ContactData contactData;

	@Valid
	@Schema(description = "Profession information")
	private CustomerProfessionInfo professionInfo;

}
