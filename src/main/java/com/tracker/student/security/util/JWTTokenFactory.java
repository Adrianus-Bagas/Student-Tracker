package com.tracker.student.security.util;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import com.tracker.student.security.model.AccessJWTToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JWTTokenFactory {
	
	private final Key secretKey;
	
	public AccessJWTToken createAccessJWTToken(String nomorInduk, Collection<? extends GrantedAuthority> authorities) {
		Claims claims = Jwts.claims().subject(nomorInduk)
		.add("scope", authorities.stream().map(a->a.getAuthority()).collect(Collectors.toList())).build();
		LocalDateTime currentTime = LocalDateTime.now();
		Date currentTimeDate = Date.from(currentTime.atZone(ZoneId.of("Asia/Jakarta")).toInstant());
		LocalDateTime expireTime = currentTime.plusMinutes(15);
		Date expireTimeDate = Date.from(expireTime.atZone(ZoneId.of("Asia/Jakarta")).toInstant());
		
		String token = Jwts.builder().claims(claims)
				.issuer("student-tracker")
				.issuedAt(currentTimeDate)
				.expiration(expireTimeDate)
				.signWith(secretKey).compact();
		
		return new AccessJWTToken(token, claims);
	}

}
