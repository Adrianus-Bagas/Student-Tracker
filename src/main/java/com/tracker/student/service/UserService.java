package com.tracker.student.service;

import com.tracker.student.dto.request.ChangePasswordFromProfileRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.UpdateUserRequestDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.UserInfoResponseDTO;
import com.tracker.student.dto.response.UserListResponseDTO;
import com.tracker.student.entity.User;

public interface UserService {

	public User findByNomorInduk(String nomorInduk);

	public PageResultResponseDTO<UserListResponseDTO> findUserList(int page, int limit, String sortBy, String direction,
			FilterSearchRequestDTO dto);

	public UserInfoResponseDTO findUserBySecureId(String secureId);

	public void updateUser(String id, UpdateUserRequestDTO dto);

	public void deleteUser(String id);

	public void changePasswordFromProfile(ChangePasswordFromProfileRequestDTO dto);

}
