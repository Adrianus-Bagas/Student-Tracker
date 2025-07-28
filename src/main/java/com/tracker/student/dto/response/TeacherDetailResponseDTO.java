package com.tracker.student.dto.response;

import lombok.Data;

@Data
public class TeacherDetailResponseDTO {

	private String id;
	private String startYear;
	private String endYear;
	private UserInfoResponseDTO user;

}
