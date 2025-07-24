package com.tracker.student.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateResultRequestDTO(@NotBlank String startYear, @NotBlank String endYear, @PositiveOrZero Float mark,
		@Positive Integer semester, @NotBlank String type, @Positive Long studentId, @Positive Long subjectId) {

}
