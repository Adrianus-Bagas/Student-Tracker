package com.tracker.student.service;

public interface EmailService {

	public void sendCredential(String recipient, String nomorInduk, String password);

	public void sendForgotPasswordURL(String recipient, String code);

}
