package com.tracker.student.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tracker.student.service.EmailService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender javaMailSender;

	@Override
	public void sendCredential(String recipient, String nomorInduk, String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipient);
		message.setSubject("Student Tracker Account Info");
		message.setText("Username : " + nomorInduk + "\n" + "Password : " + password);
		javaMailSender.send(message);
	}

}
