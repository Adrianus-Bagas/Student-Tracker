package com.tracker.student.service;

import com.tracker.student.dto.RegisterRequestDTO;
import com.tracker.student.entity.User;

public interface UserService {

	public User createUser(RegisterRequestDTO dto);

}
