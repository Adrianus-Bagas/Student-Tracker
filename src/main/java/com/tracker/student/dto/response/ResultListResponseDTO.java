package com.tracker.student.dto.response;

import lombok.Data;

@Data
public class ResultListResponseDTO {

	private String id;
	private String startYear;
	private String endYear;
	private Integer semester;
	private Float mark;
	private String type;
	private Boolean isPassed;
	private SubjectDetailResultResponseDTO subject;

}
