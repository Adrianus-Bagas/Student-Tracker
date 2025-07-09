package com.tracker.student.dto.request;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChangePasswordFromProfileRequestDTO(@NotBlank String nomorInduk,
		@NotBlank @Length(min = 8) String oldPassword, @NotBlank @Length(min = 8) String newPassword,
		@NotBlank @Length(min = 8) String confirmNewPassword) {

}
