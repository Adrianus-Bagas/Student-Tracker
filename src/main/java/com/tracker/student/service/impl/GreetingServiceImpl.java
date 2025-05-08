package com.tracker.student.service.impl;

import org.springframework.stereotype.Service;

import com.tracker.student.config.ApplicationProperties;
import com.tracker.student.service.GreetingService;

@Service
public class GreetingServiceImpl implements GreetingService {
	
	private ApplicationProperties applicationProperties;
	
	public GreetingServiceImpl(ApplicationProperties applicationProperties) {
		super();
		this.applicationProperties = applicationProperties;
	}

	@Override
	public String sayHello() {
		return this.applicationProperties.getWelcomeText()+", Timezone: "+this.applicationProperties.getTimezone()+", Currency: "+applicationProperties.getCurrency();
	}

}
