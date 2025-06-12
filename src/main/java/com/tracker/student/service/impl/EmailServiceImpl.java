package com.tracker.student.service.impl;

import java.util.Date;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tracker.student.config.ApplicationProperties;
import com.tracker.student.entity.User;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.UserRepository;
import com.tracker.student.service.EmailService;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	private final ApplicationProperties applicationProperties;

	@Override
	public void sendCredential(String recipient, String nomorInduk, String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipient);
		message.setSubject("Student Tracker Account Info");
		message.setText("Username : " + nomorInduk + "\n" + "Password : " + password);
		javaMailSender.send(message);
	}

	@Override
	public void sendForgotPasswordURL(String recipient, String code) {
		User user = userRepository.findByEmail(recipient).orElse(new User());
		Date now = new Date();
		Date nowPlusTenMinutes = new Date(now.getTime() + applicationProperties.getExpiredForgotPassword());
		if (StringUtils.isBlank(user.getNomorInduk())) {
			throw new BadRequestException("User tidak ditemukan");
		}
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipient);
		message.setSubject("Forgot Password Student Tracker");
		message.setText("Waktu untuk mengganti password sampai " + nowPlusTenMinutes.toString()
				+ "\nhttps://student-tracker.com?code=" + code);
		javaMailSender.send(message);
	}

}
