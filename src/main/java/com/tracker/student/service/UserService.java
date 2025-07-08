package com.tracker.student.service;

import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.UserListResponseDTO;
import com.tracker.student.entity.User;

public interface UserService {

	public User findByNomorInduk(String nomorInduk);

	public PageResultResponseDTO<UserListResponseDTO> findUserList(int page, int limit, String sortBy, String direction,
			String name);

}
