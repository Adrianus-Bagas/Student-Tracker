package com.tracker.student.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateResultRequestDTO(String startYear, String endYear, Float mark,
		Integer semester, String type, Long studentId, Long subjectId) {

}
