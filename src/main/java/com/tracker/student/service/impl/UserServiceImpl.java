package com.tracker.student.service.impl;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tracker.student.dto.UserDetailResponseDTO;
import com.tracker.student.repository.UserRepository;
import com.tracker.student.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByNomorInduk(username).orElseThrow();
	}

	@Override
	public UserDetailResponseDTO findUserDetail() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		UserDetailResponseDTO dto = new UserDetailResponseDTO();
		String nomorInduk = ctx.getAuthentication().getName();
		dto.setNomorInduk(nomorInduk);
		return null;
	}

}
