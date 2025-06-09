package com.tracker.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.LoginRequestDTO;
import com.tracker.student.dto.LoginResponseDTO;
import com.tracker.student.dto.RegisterRequestDTO;
import com.tracker.student.entity.User;
import com.tracker.student.security.util.JwtUtils;
import com.tracker.student.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	@PostMapping("/register")
	public User register(@RequestBody RegisterRequestDTO dto) {
		return userService.createUser(dto);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.nomorInduk(), dto.password()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		return ResponseEntity.ok().body(new LoginResponseDTO(token, principal.getUsername()));
	}

}
