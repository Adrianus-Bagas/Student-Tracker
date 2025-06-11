package com.tracker.student.util;

import java.time.Instant;

import lombok.Data;

@Data
public class CustomResponseError {

	private String message;
	private int errorCode;
	private String timestamp;

	public CustomResponseError(String message, int errorCode) {
		this.message = message;
		this.errorCode = errorCode;
		this.timestamp = Instant.now().toString();
	}

}
