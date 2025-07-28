package com.tracker.student.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateSubjectRequestDTO(@NotBlank String name, @NotBlank String startYear, @NotBlank String endYear,
		@Min(60) @Max(70) Integer minimum, @Positive Long teacherId) {

}
