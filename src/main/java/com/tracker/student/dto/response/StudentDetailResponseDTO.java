package com.tracker.student.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentDetailResponseDTO {
	
	private String id;
	private String startYear;
	private String endYear;
	private boolean isPromoted;
	private UserInfoResponseDTO user;
	private ClassDetailResponseDTO studentClass;

}
