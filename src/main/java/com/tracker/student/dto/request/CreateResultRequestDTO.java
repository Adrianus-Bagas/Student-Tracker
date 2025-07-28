package com.tracker.student.dto.request;

import com.tracker.student.constants.ResultTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateResultRequestDTO(@NotBlank String startYear, @NotBlank String endYear, @PositiveOrZero Float mark,
		@Positive Integer semester, @NotBlank ResultTypes type, @Positive Long studentId, @Positive Long subjectId) {

}
