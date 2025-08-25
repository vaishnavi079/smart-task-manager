package com.smarttaskmanager.notification_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.smarttaskmanager.common.bean.NotificationDto;

@Service
public class NotificationService {
	
	   @Autowired
	   EmailService emailService;
	
	   @KafkaListener(topics = "Email-Notification", groupId = "Task-Created")
	   public void consume(NotificationDto notifyConsumer) {
		   emailService.sendEmail(notifyConsumer.getEmail(), "Update on Task", notifyConsumer.getMessage());
	   }
	   
}
