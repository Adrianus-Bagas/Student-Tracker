package com.tracker.student.dto.response;

public record LoginResponseDTO(String accessToken, String nomorInduk, String refreshToken, String createdAt,
		String expiredAt) {

}
