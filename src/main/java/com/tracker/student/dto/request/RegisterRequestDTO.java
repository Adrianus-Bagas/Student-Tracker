package com.tracker.student.dto.request;

import com.tracker.student.constants.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RegisterRequestDTO(@NotBlank String nomorInduk, @NotBlank @Email String email, @NotBlank String name,
		@Positive Integer age, @NotBlank Roles role, @NotBlank String startYear, @NotBlank String endYear,
		Long classId) {

}
