package com.tracker.student.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tracker.student.dto.UserDetailResponseDTO;

public interface UserService extends UserDetailsService {
	
	public UserDetailResponseDTO findUserDetail();

}
