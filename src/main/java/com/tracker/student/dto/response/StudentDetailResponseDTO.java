package com.tracker.student.dto.response;

import lombok.Data;

@Data
public class StudentDetailResponseDTO {

	private String id;
	private String startYear;
	private String endYear;
	private boolean isPromoted;
	private UserInfoResponseDTO user;
	private ClassDetailResponseDTO studentClass;

}
