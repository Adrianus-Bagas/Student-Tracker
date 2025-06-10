package com.tracker.student.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomResponseError {
	
	private String message;
	private int errorCode;

}
