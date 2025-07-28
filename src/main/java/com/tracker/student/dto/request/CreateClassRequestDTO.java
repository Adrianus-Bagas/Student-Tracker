package com.tracker.student.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateClassRequestDTO(@NotBlank String startYear, @NotBlank String endYear, @NotBlank String name) {

}
