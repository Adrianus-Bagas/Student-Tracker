package com.tracker.student.service.impl;

import org.springframework.stereotype.Service;

import com.tracker.student.entity.User;
import com.tracker.student.repository.UserRepository;
import com.tracker.student.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Override
	public User findByNomorInduk(String nomorInduk) {
		return userRepository.findByNomorInduk(nomorInduk).orElse(new User());
	}

}
