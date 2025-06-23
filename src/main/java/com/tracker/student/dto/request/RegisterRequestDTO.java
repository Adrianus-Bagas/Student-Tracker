package com.tracker.student.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RegisterRequestDTO(@NotBlank String nomorInduk, @NotBlank @Email String email, @NotBlank String name,
		@Positive int age, @NotBlank String role, @NotBlank String startYear, @NotBlank String endYear, Long classId) {

}
