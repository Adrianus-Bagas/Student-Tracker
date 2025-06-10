package com.tracker.student.service;

public interface EmailService {

	public void sendCredential(String recipient, String nomorInduk, String password);

}
