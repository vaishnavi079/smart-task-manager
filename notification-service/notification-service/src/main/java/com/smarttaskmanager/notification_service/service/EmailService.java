package com.smarttaskmanager.notification_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

		@Autowired
		private JavaMailSender sender;
		
		public void sendEmail(String to, String sub, String msg) {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setFrom("vaishnavibandaru579@gmail.com");
			mail.setTo(to);
			mail.setSubject(sub);
			mail.setText(msg);
			sender.send(mail);
		}
}
