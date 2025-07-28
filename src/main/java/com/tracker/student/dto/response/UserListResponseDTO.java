package com.tracker.student.dto.response;

import com.tracker.student.constants.Roles;

import lombok.Data;

@Data
public class UserListResponseDTO {

	private String userId;
	private String nomorInduk;
	private String name;
	private String email;
	private Roles role;

}
