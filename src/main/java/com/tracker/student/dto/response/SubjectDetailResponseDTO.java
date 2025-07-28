package com.tracker.student.dto.response;

import lombok.Data;

@Data
public class SubjectDetailResponseDTO {

	private String id;
	private String startYear;
	private String endYear;
	private String name;
	private int minimum;
	private TeacherDetailResponseDTO teacher;

}
