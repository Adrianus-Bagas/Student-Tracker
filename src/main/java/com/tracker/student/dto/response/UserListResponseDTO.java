package com.tracker.student.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserListResponseDTO {

	private String userId;
	private String nomorInduk;
	private String name;
	private String email;
	private String role;

}
