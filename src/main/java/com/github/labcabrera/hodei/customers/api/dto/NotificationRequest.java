package com.github.labcabrera.hodei.customers.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

	private NotificationType type;

	private final String module = "hodei-customers";

	private String operation;

	private String subject;

	private String body;

}
