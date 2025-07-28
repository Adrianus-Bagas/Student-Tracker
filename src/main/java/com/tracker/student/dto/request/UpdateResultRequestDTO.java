package com.tracker.student.dto.request;

import com.tracker.student.constants.ResultTypes;

public record UpdateResultRequestDTO(String startYear, String endYear, Float mark, Integer semester, ResultTypes type,
		Long studentId, Long subjectId) {

}
