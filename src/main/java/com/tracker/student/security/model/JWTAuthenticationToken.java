package com.tracker.student.security.model;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

	private RawAccessJWTToken rawAccessJWTToken;

	private UserDetails userDetails;

	public JWTAuthenticationToken(RawAccessJWTToken rawAccessJWTToken) {
		super(null);
		this.rawAccessJWTToken = rawAccessJWTToken;
		super.setAuthenticated(false);
	}

	public JWTAuthenticationToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.eraseCredentials();
		this.userDetails = userDetails;
		this.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.rawAccessJWTToken;
	}

	@Override
	public Object getPrincipal() {
		return this.userDetails;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.rawAccessJWTToken = null;
	}

}
