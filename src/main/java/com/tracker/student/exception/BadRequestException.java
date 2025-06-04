package com.tracker.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    
    private static final long serialVersionUID = -6216080434910952280L;

	public BadRequestException(String message){
        super(message);
    }
}
