package com.tracker.student.config;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.student.security.util.JWTTokenFactory;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class AppConfig {
		
	@Autowired
	private ApplicationProperties applicationProperties;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecretKey secretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }
	
	@Bean
	public Key key() {
		byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.getSecretKey());
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	@Bean
	public JWTTokenFactory jwtTokenFactory(Key secretKey) {
		return new JWTTokenFactory(secretKey); 
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
}
