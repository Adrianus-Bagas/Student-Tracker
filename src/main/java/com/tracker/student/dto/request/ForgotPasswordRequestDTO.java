package com.tracker.student.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequestDTO(@NotBlank String nomorInduk, @Email @NotBlank String email) {

}
