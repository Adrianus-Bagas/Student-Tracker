package com.tracker.student.service;

import org.apache.tomcat.websocket.AuthenticationException;

import com.tracker.student.dto.LoginRequestDTO;
import com.tracker.student.dto.LoginResponseDTO;
import com.tracker.student.dto.RefreshTokenRequestDTO;
import com.tracker.student.dto.RegisterRequestDTO;

public interface AuthService {
	
	public void createUser(RegisterRequestDTO dto);
	public LoginResponseDTO authenticate(LoginRequestDTO dto);
	public LoginResponseDTO refreshToken(RefreshTokenRequestDTO refreshToken) throws AuthenticationException;

}
