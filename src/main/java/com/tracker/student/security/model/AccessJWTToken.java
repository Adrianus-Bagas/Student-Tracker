package com.tracker.student.security.model;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessJWTToken implements TokenInterface {
	
	private String rawToken;
	
	private Claims claims;

	@Override
	public String getToken() {
		return this.rawToken;
	}

}
