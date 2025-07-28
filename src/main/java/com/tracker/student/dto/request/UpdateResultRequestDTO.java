package com.tracker.student.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tracker.student.constants.ResultTypes;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateResultRequestDTO(String startYear, String endYear, Float mark, Integer semester, ResultTypes type,
		Long studentId, Long subjectId) {

}
