package com.tracker.student.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(@NotBlank String refreshToken) {

}
