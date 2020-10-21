package com.github.labcabrera.hodei.customers.api.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.github.labcabrera.hodei.customers.api.binding.NotificationsSource;
import com.github.labcabrera.hodei.customers.api.dto.NotificationRequest;
import com.github.labcabrera.hodei.customers.api.dto.NotificationType;
import com.github.labcabrera.hodei.model.commons.customer.Customer;

@Service
@EnableBinding(NotificationsSource.class)
public class NotificationService {

	@Autowired
	private NotificationsSource notificationsSource;

	public void customerCreation(Customer customer) {
		StringBuilder subject = new StringBuilder("Created person ");
		subject.append(customer.getName()).append(" ").append(customer.getSurname1());
		if (StringUtils.isNotBlank(customer.getSurname2())) {
			subject.append(" ").append(customer.getSurname2());
		}
		subject.append(" - ").append(customer.getIdCard().getNumber());
		subject.append(" (").append(customer.getId()).append(")");

		NotificationRequest notificationrequest = NotificationRequest.builder()
			.type(NotificationType.NOTIFICATION)
			.operation("customer-creation")
			.subject(subject.toString())
			.build();
		notificationsSource.sendNotificationChannel().send(MessageBuilder.withPayload(notificationrequest).build());
	}

	public void customerModification(Customer customer) {
		StringBuilder subject = new StringBuilder("Updated person ");
		subject.append(customer.getName()).append(" ").append(customer.getSurname1());
		if (StringUtils.isNotBlank(customer.getSurname2())) {
			subject.append(" ").append(customer.getSurname2());
		}
		subject.append(" - ").append(customer.getIdCard().getNumber());
		subject.append(" (").append(customer.getId()).append(")");

		NotificationRequest notificationrequest = NotificationRequest.builder()
			.type(NotificationType.NOTIFICATION)
			.operation("customer-creation")
			.subject(subject.toString())
			.build();
		notificationsSource.sendNotificationChannel().send(MessageBuilder.withPayload(notificationrequest).build());
	}
}
