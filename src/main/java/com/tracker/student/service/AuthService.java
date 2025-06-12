package com.tracker.student.service;

import org.apache.tomcat.websocket.AuthenticationException;

import com.tracker.student.dto.request.ChangePasswordFromEmailDTO;
import com.tracker.student.dto.request.ForgotPasswordRequestDTO;
import com.tracker.student.dto.request.LoginRequestDTO;
import com.tracker.student.dto.request.RefreshTokenRequestDTO;
import com.tracker.student.dto.request.RegisterRequestDTO;
import com.tracker.student.dto.response.LoginResponseDTO;

public interface AuthService {

	public void createUser(RegisterRequestDTO dto);

	public LoginResponseDTO authenticate(LoginRequestDTO dto);

	public LoginResponseDTO refreshToken(RefreshTokenRequestDTO refreshToken) throws AuthenticationException;

	public void forgotPassword(ForgotPasswordRequestDTO dto);

	public void changePasswordFromEmail(ChangePasswordFromEmailDTO dto);

}
