package com.tracker.student.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.student.security.handler.UsernamePasswordAuthSuccessHandler;
import com.tracker.student.security.provider.UsernamePasswordAuthProvider;
import com.tracker.student.security.util.JWTTokenFactory;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	private UsernamePasswordAuthProvider usernamePasswordAuthProvider;
	
	public AuthenticationSuccessHandler usernamePasswordAuthSuccessHandler(ObjectMapper objectMapper, JWTTokenFactory jwtTokenFactory) {
		return new UsernamePasswordAuthSuccessHandler(objectMapper, jwtTokenFactory);
	}

	@Autowired
	void registerProvider(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
		auth.authenticationProvider(usernamePasswordAuthProvider);
	}
	
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
}
