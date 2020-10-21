package com.github.labcabrera.hodei.customers.api.service.customer.modification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.labcabrera.hodei.customers.api.dto.CustomerModification;
import com.github.labcabrera.hodei.customers.api.model.CustomerModificationResult.CustomerModificationField;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

@Component
public class CustomerFieldModificationService {

	@Autowired
	private ObjectMapper objectMapper;

	public List<CustomerModificationField> merge(Customer customer, CustomerModification request) {
		List<CustomerModificationField> list = new ArrayList<>();

		if (checkField("name", customer.getName(), request.getName(), list)) {
			customer.setName(request.getName());
		}

		if (checkField("surname1", customer.getSurname1(), request.getSurname1(), list)) {
			customer.setSurname1(request.getSurname1());
		}

		if (checkField("surname2", customer.getSurname2(), request.getSurname2(), list)) {
			customer.setSurname2(request.getSurname2());
		}

		if (checkField("birth", customer.getBirth(), request.getBirth(), list)) {
			customer.setBirth(request.getBirth());
		}

		if (checkField("birthCountryId", customer.getBirthCountryId(), request.getBirthCountryId(), list)) {
			customer.setBirthCountryId(request.getBirthCountryId());
		}

		if (checkField("civilStatus", customer.getCivilStatus(), request.getCivilStatus(), list)) {
			customer.setCivilStatus(request.getCivilStatus());
		}

		if (checkField("gender", customer.getGender(), request.getGender(), list)) {
			customer.setGender(request.getGender());
		}

		if (checkFieldJson("idCard", customer.getIdCard(), request.getIdCard(), list)) {
			customer.setIdCard(request.getIdCard());
		}

		if (checkFieldJson("nationalities", customer.getNationalities(), request.getNationalities(), list)) {
			customer.setNationalities(request.getNationalities());
		}

		if (checkFieldJson("fiscalAddress", customer.getFiscalAddress(), request.getFiscalAddress(), list)) {
			customer.setFiscalAddress(request.getFiscalAddress());
		}

		if (checkFieldJson("contactData", customer.getContactData(), request.getContactData(), list)) {
			customer.setContactData(request.getContactData());
		}

		if (checkFieldJson("professionInfo", customer.getProfessionInfo(), request.getProfessionInfo(), list)) {
			customer.setProfessionInfo(request.getProfessionInfo());
		}

		if (checkFieldJson("commercialNotifications", customer.getCommercialNotifications(), request.getCommercialNotifications(), list)) {
			customer.setCommercialNotifications(request.getCommercialNotifications());
		}

		return list;
	}

	private boolean checkField(String field, Object current, Object received, List<CustomerModificationField> list) {
		if (received != null && !received.equals(current)) {
			list.add(CustomerModificationField.builder()
				.field(field)
				.before(current.toString())
				.after(received.toString())
				.build());
			return true;
		}
		return false;
	}

	private boolean checkFieldJson(String field, Object current, Object received, List<CustomerModificationField> list) {
		if (received == null) {
			return false;
		}
		try {
			String jsonCurrent = current != null ? objectMapper.writeValueAsString(current) : null;
			String jsonReceived = objectMapper.writeValueAsString(received);
			if (!jsonReceived.equals(jsonCurrent)) {
				list.add(CustomerModificationField.builder()
					.field(field)
					.before(jsonCurrent)
					.after(jsonReceived)
					.build());
				return true;
			}
			return false;
		}
		catch (JsonProcessingException ex) {
			throw new RuntimeException("Error checking field " + field, ex);
		}
	}

}
