package com.tracker.student.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordFromProfileRequestDTO(@NotBlank String nomorInduk,
		@NotBlank @Length(min = 8) String oldPassword, @NotBlank @Length(min = 8) String newPassword,
		@NotBlank @Length(min = 8) String confirmNewPassword) {

}
