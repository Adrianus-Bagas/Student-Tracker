package com.tracker.student.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateClassRequestDTO(@NotBlank String startYear, @NotBlank String endYear, @NotBlank String name,
		@NotBlank Long teacherId) {

}
