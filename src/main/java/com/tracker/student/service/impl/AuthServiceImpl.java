package com.tracker.student.service.impl;

import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tracker.student.dto.LoginRequestDTO;
import com.tracker.student.dto.LoginResponseDTO;
import com.tracker.student.dto.RefreshTokenRequestDTO;
import com.tracker.student.dto.RegisterRequestDTO;
import com.tracker.student.entity.User;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.UserRepository;
import com.tracker.student.security.util.JwtUtils;
import com.tracker.student.service.AuthService;
import com.tracker.student.service.EmailService;
import com.tracker.student.util.GenerateRandomPassword;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public void createUser(RegisterRequestDTO dto) {
		GenerateRandomPassword generatedPassword = new GenerateRandomPassword();
		String password = generatedPassword.randomPassword(8);
		User user = userRepository.findByNomorInduk(dto.nomorInduk()).orElse(new User());
		Boolean isStartYearBiggerThanEqualToEndYear = dto.startYear() >= dto.endYear();
		Boolean isYearDifferenceMoreThanOne = dto.endYear() - dto.startYear() > 1;
		if (isStartYearBiggerThanEqualToEndYear || isYearDifferenceMoreThanOne) {
			throw new BadRequestException("Tahun Ajaran tidak Valid");
		}
		if (!StringUtils.isBlank(user.getNomorInduk())) {
			throw new BadRequestException("Nomor Induk sudah Terdaftar");
		}
		try {
			user.setNomorInduk(dto.nomorInduk());
			user.setPassword(passwordEncoder.encode(password));
			user.setEmail(dto.email());
			user.setName(dto.name());
			user.setAge(dto.age());
			user.setRole(dto.role());
			user.setStartYear(dto.startYear());
			user.setEndYear(dto.endYear());
			userRepository.save(user);
			emailService.sendCredential(user.getEmail(), dto.nomorInduk(), password);
		} catch (Exception e) {
			logger.error("Failed to save user");
			throw new BadRequestException("gagal menambahkan user");
		}
	}

	@Override
	public LoginResponseDTO authenticate(LoginRequestDTO dto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.nomorInduk(), dto.password()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String accessToken = jwtUtils.generateJwtToken(principal.getUsername());
		String refreshToken = jwtUtils.generateJwtRefreshToken(principal.getUsername());
		String createdAt = jwtUtils.getCreatedAccessToken(accessToken);
		String expiredAt = jwtUtils.getExpiredAccessToken(accessToken);

		return new LoginResponseDTO(accessToken, principal.getUsername(), refreshToken, createdAt, expiredAt);
	}

	@Override
	public LoginResponseDTO refreshToken(RefreshTokenRequestDTO dto) throws AuthenticationException {

		try {
			jwtUtils.validateJwtToken(dto.refreshToken());
		} catch (Exception e) {
			throw new BadRequestException("Invalid token");
		}

		if (!jwtUtils.validateJwtToken(dto.refreshToken())) {
			throw new BadRequestException("Invalid token");
		}
		String nomorInduk = jwtUtils.getUserNameFromJwtToken(dto.refreshToken());
		String accessToken = jwtUtils.generateJwtToken(nomorInduk);
		String refreshToken = jwtUtils.generateJwtRefreshToken(nomorInduk);
		String createdAt = jwtUtils.getCreatedAccessToken(accessToken);
		String expiredAt = jwtUtils.getExpiredAccessToken(accessToken);
		return new LoginResponseDTO(accessToken, nomorInduk, refreshToken, createdAt, expiredAt);

	}

}
