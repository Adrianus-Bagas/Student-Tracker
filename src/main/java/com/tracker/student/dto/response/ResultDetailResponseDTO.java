package com.tracker.student.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResultDetailResponseDTO {

	private String id;
	private String startYear;
	private String endYear;
	private String type;
	private int semester;
	private float mark;
	private boolean isPassed;
	private SubjectDetailResponseDTO subject;
	private StudentDetailResponseDTO student;
	
}
