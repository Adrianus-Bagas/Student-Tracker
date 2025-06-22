package com.tracker.student.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChangePasswordFromEmailDTO(@NotBlank String code, @NotBlank @Min(8) String oldPassword,
		@NotBlank @Min(8) String newPassword, @NotBlank @Min(8) String confirmNewPassword) {

}
