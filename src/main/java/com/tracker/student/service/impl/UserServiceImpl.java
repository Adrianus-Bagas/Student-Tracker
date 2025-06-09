package com.tracker.student.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tracker.student.dto.RegisterRequestDTO;
import com.tracker.student.entity.User;
import com.tracker.student.repository.UserRepository;
import com.tracker.student.service.UserService;
import com.tracker.student.util.GenerateRandomPassword;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	public User createUser(RegisterRequestDTO dto) {
		GenerateRandomPassword generatedPassword = new GenerateRandomPassword();
		String password = generatedPassword.randomPassword(8);
		logger.info(password);
		User user = new User();
		user.setNomorInduk(dto.nomorInduk());
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(dto.email());
		user.setName(dto.name());
		user.setAge(dto.age());
		user.setRole(dto.role());
		return userRepository.save(user);
	}

}
