package com.tracker.student.dto.response;

import lombok.Data;

@Data
public class SubjectDetailResultResponseDTO {

	private String id;
	private String startYear;
	private String endYear;
	private String name;
	private int minimum;

}
