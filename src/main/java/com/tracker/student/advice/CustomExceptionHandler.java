package com.tracker.student.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tracker.student.exception.BadRequestException;
import com.tracker.student.util.CustomResponseError;

@ControllerAdvice
public class CustomExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<CustomResponseError> handleUserNotFoundException(BadRequestException exception) {
		CustomResponseError error = new CustomResponseError(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
