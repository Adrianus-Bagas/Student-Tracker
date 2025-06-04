package com.tracker.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.UserDetailResponseDTO;
import com.tracker.student.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private UserService userService;
	
	@GetMapping("/v1/user")
	public ResponseEntity<UserDetailResponseDTO> findUserDetail() {
		return ResponseEntity.ok(userService.findUserDetail());
	}

}
