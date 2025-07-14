package com.tracker.student.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeacherDetailResponseDTO {

	private String id;
	private String startYear;
	private String endYear;
	private UserInfoResponseDTO user;

}
