package com.tracker.student.controller;

import java.net.URI;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.request.ChangePasswordFromEmailDTO;
import com.tracker.student.dto.request.ForgotPasswordRequestDTO;
import com.tracker.student.dto.request.LoginRequestDTO;
import com.tracker.student.dto.request.RefreshTokenRequestDTO;
import com.tracker.student.dto.request.RegisterRequestDTO;
import com.tracker.student.dto.response.LoginResponseDTO;
import com.tracker.student.service.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/v1/register")
	public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO dto) {
		authService.createUser(dto);
		return ResponseEntity.created(URI.create("/register")).build();
	}

	@PostMapping("/v1/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
		return ResponseEntity.ok().body(authService.authenticate(dto));
	}

	@PostMapping("/v1/refresh-token")
	public ResponseEntity<LoginResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO dto)
			throws AuthenticationException {
		LoginResponseDTO response = authService.refreshToken(dto);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/v1/forgot-password")
	public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO dto)
			throws AuthenticationException {
		authService.forgotPassword(dto);
		return ResponseEntity.created(URI.create("/forgot-password")).build();
	}

	@PostMapping("/v1/change-password-from-email")
	public ResponseEntity<Void> changePasswordFromEmail(@Valid @RequestBody ChangePasswordFromEmailDTO dto)
			throws AuthenticationException {
		authService.changePasswordFromEmail(dto);
		return ResponseEntity.created(URI.create("/change-password-from-email")).build();
	}

}
