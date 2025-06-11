package com.tracker.student.security.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tracker.student.config.ApplicationProperties;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	@Autowired
	private ApplicationProperties applicationProperties;

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	private SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(applicationProperties.getSecretKey()));
	}

	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT Token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT Token Expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT Token Unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT Claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String generateJwtToken(String nomorInduk) {
		return Jwts.builder().subject(nomorInduk).issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + applicationProperties.getExpiredMs()))
				.signWith(key(), Jwts.SIG.HS256).compact();
	}

	public String generateJwtRefreshToken(String nomorInduk) {
		return Jwts.builder().subject(nomorInduk).issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + applicationProperties.getExpiredRefreshToken()))
				.signWith(key(), Jwts.SIG.HS256).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	public String getCreatedAccessToken(String token) {
		return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getIssuedAt().toString();
	}

	public String getExpiredAccessToken(String token) {
		return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getExpiration().toString();
	}
}
