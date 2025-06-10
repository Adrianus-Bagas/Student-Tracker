package com.tracker.student.controller;

import java.net.URI;

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
import com.tracker.student.util.CustomResponseError;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
		User user = userService.findByNomorInduk(dto.nomorInduk());
		if(StringUtils.isNotBlank(user.getNomorInduk())) {			
			return ResponseEntity.badRequest().body(new CustomResponseError("Nomor Induk telah Terdaftar", HttpServletResponse.SC_BAD_REQUEST));
		}
		Boolean isStartYearBiggerThanEqualToEndYear = dto.startYear() >= dto.endYear();
		Boolean isYearDifferenceMoreThanOne = dto.endYear() - dto.startYear() > 1;
		if (isStartYearBiggerThanEqualToEndYear || isYearDifferenceMoreThanOne) {
			return ResponseEntity.badRequest().body(new CustomResponseError("Tahun Ajaran tidak Valid", HttpServletResponse.SC_BAD_REQUEST));
		}
		userService.createUser(dto);
		return ResponseEntity.created(URI.create("/register")).build();
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
