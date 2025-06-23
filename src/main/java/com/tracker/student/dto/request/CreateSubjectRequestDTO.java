package com.tracker.student.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateSubjectRequestDTO(@NotBlank String name, @NotBlank String startYear, @NotBlank String endYear,
		@Min(60) @Max(70) int minimum, @Positive Long teacherId) {

}
