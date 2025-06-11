package com.tracker.student.controller;

import java.net.URI;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.LoginRequestDTO;
import com.tracker.student.dto.LoginResponseDTO;
import com.tracker.student.dto.RefreshTokenRequestDTO;
import com.tracker.student.dto.RegisterRequestDTO;
import com.tracker.student.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegisterRequestDTO dto) {
		authService.createUser(dto);
		return ResponseEntity.created(URI.create("/register")).build();
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
		return ResponseEntity.ok().body(authService.authenticate(dto));
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO dto)
			throws AuthenticationException {
		LoginResponseDTO response = authService.refreshToken(dto);
		return ResponseEntity.ok(response);
	}

}
