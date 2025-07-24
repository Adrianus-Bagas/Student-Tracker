package com.tracker.student.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Max;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateSubjectRequestDTO(String name, String startYear, String endYear, @Max(70) int minimum,
		Long teacherId) {

}
