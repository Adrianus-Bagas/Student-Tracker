package com.tracker.student.service;

import com.tracker.student.dto.RegisterRequestDTO;
import com.tracker.student.entity.User;

public interface UserService {

	public void createUser(RegisterRequestDTO dto);
	public User findByNomorInduk(String nomorInduk);

}
