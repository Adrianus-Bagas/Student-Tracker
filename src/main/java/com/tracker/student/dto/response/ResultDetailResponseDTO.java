package com.tracker.student.dto.response;

import com.tracker.student.constants.ResultTypes;

import lombok.Data;

@Data
public class ResultDetailResponseDTO {

	private String id;
	private String startYear;
	private String endYear;
	private ResultTypes type;
	private int semester;
	private float mark;
	private boolean isPassed;
	private SubjectDetailResponseDTO subject;
	private StudentDetailResponseDTO student;

}
