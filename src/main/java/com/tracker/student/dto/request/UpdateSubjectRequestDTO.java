package com.tracker.student.dto.request;

import jakarta.validation.constraints.Max;

public record UpdateSubjectRequestDTO(String name, String startYear, String endYear, @Max(70) Integer minimum,
		Long teacherId) {

}
