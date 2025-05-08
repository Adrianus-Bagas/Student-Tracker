package com.tracker.student.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.service.GreetingService;

@RestController
public class HelloWorldController {
	
	private final GreetingService greetingService;

	public HelloWorldController(GreetingService greetingService) {
		super();
		this.greetingService = greetingService;
	}

	@GetMapping("/hello")
	public String HelloWorld() {
		return greetingService.sayHello();
	}
}
