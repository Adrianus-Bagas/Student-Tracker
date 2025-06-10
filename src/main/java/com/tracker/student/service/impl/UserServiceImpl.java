package com.tracker.student.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tracker.student.dto.RegisterRequestDTO;
import com.tracker.student.entity.User;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.UserRepository;
import com.tracker.student.service.EmailService;
import com.tracker.student.service.UserService;
import com.tracker.student.util.GenerateRandomPassword;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private EmailService emailService;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	public void createUser(RegisterRequestDTO dto) {
		GenerateRandomPassword generatedPassword = new GenerateRandomPassword();
		String password = generatedPassword.randomPassword(8);
		logger.info(password);
		User user = userRepository.findByNomorInduk(dto.nomorInduk()).orElse(new User());
		logger.info(user.getNomorInduk());
		Boolean isStartYearBiggerThanEqualToEndYear = dto.startYear() >= dto.endYear();
		Boolean isYearDifferenceMoreThanOne = dto.endYear() - dto.startYear() > 1;
		if (isStartYearBiggerThanEqualToEndYear || isYearDifferenceMoreThanOne) {
			throw new BadRequestException("tahun ajaran invalid");
		}
		if (!StringUtils.isBlank(user.getNomorInduk())) {
			throw new BadRequestException("nomor induk sudah terdaftar");
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
	public User findByNomorInduk(String nomorInduk) {
		return userRepository.findByNomorInduk(nomorInduk).orElse(new User());
	}

}
