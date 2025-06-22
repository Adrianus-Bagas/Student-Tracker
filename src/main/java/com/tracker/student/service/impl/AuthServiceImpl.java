package com.tracker.student.service.impl;

import java.util.Date;
import java.util.UUID;

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

import com.tracker.student.config.ApplicationProperties;
import com.tracker.student.dto.request.ChangePasswordFromEmailDTO;
import com.tracker.student.dto.request.ForgotPasswordRequestDTO;
import com.tracker.student.dto.request.LoginRequestDTO;
import com.tracker.student.dto.request.RefreshTokenRequestDTO;
import com.tracker.student.dto.request.RegisterRequestDTO;
import com.tracker.student.dto.response.LoginResponseDTO;
import com.tracker.student.entity.Teacher;
import com.tracker.student.entity.User;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.TeacherRepository;
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
	private final TeacherRepository teacherRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final ApplicationProperties applicationProperties;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public void createUser(RegisterRequestDTO dto) {
		GenerateRandomPassword generatedPassword = new GenerateRandomPassword();
		String password = generatedPassword.randomPassword(8);
		User user = userRepository.findByNomorInduk(dto.nomorInduk()).orElse(new User());
		Boolean isStartYearBiggerThanEqualToEndYear = Integer.parseInt(dto.startYear()) >= Integer
				.parseInt(dto.endYear());
		Boolean isYearDifferenceMoreThanOne = Integer.parseInt(dto.endYear()) - Integer.parseInt(dto.startYear()) > 1;
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
			if (dto.role().equalsIgnoreCase("TEACHER")) {
				Teacher teacher = new Teacher();
				teacher.setStartYear(dto.startYear());
				teacher.setEndYear(dto.endYear());
				teacher.setUser(user);
				teacherRepository.save(teacher);
			}
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

	@Override
	public void forgotPassword(ForgotPasswordRequestDTO dto) {
		User user = userRepository.findByNomorInduk(dto.nomorInduk()).orElse(new User());
		if (StringUtils.isBlank(user.getNomorInduk())) {
			throw new BadRequestException("User tidak ditemukan");
		}
		if (!user.getEmail().matches(dto.email())) {
			throw new BadRequestException("Email yang digunakan tidak sesuai");
		}
		String forgotPasswordCode = UUID.randomUUID().toString();
		Date now = new Date();
		Date nowPlusTenMinutes = new Date(now.getTime() + applicationProperties.getExpiredForgotPassword());
		user.setForgotPasswordCode(forgotPasswordCode);
		user.setForgotPasswordCodeExpiredAt(nowPlusTenMinutes);
		userRepository.save(user);
		emailService.sendForgotPasswordURL(dto.email(), forgotPasswordCode);
	}

	@Override
	public void changePasswordFromEmail(ChangePasswordFromEmailDTO dto) {
		User user = userRepository.findByForgotPasswordCode(dto.code()).orElse(new User());
		if (StringUtils.isBlank(user.getNomorInduk())) {
			throw new BadRequestException("Link sudah kedaluwarsa");
		}
		Date now = new Date();
		if (now.getTime() > user.getForgotPasswordCodeExpiredAt().getTime()) {
			user.setForgotPasswordCode(null);
			user.setForgotPasswordCodeExpiredAt(null);
			userRepository.save(user);
			throw new BadRequestException("Link sudah kedaluwarsa");
		}
		if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
			throw new BadRequestException("Password lama tidak sesuai");
		}
		if (!dto.newPassword().matches(dto.confirmNewPassword())) {
			throw new BadRequestException("Mohon password baru dan konfirmasi password baru disamakan");
		}
		user.setPassword(passwordEncoder.encode(dto.newPassword()));
		user.setForgotPasswordCode(null);
		user.setForgotPasswordCodeExpiredAt(null);
		userRepository.save(user);
	}

}
