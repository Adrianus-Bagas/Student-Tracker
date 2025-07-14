package com.tracker.student.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StudentDetailResponseDTO(String id, String startYear, String endYear, boolean isPromoted,
		UserInfoResponseDTO user, ClassDetailResponseDTO studentClass) {

}
