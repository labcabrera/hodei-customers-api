package com.github.labcabrera.hodei.customers.api.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface NotificationsSource {

	@Output("sendNotification")
	MessageChannel sendNotificationChannel();

}