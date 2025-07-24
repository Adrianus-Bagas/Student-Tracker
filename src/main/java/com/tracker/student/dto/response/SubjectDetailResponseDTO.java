package com.tracker.student.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectDetailResponseDTO {

	private String id;
	private String startYear;
	private String endYear;
	private String name;
	private int minimum;
	private TeacherDetailResponseDTO teacher;

}
