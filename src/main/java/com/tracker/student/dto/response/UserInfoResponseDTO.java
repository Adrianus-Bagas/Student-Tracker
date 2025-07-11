package com.tracker.student.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoResponseDTO {

	private String id;
	private String name;
	private String email;
	private int age;
	private String role;
	private String nomorInduk;
	private String startYear;
	private String endYear;
}
