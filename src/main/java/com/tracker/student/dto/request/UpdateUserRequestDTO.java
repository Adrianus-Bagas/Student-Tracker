package com.tracker.student.dto.request;

import jakarta.validation.constraints.Email;

public record UpdateUserRequestDTO(String nomorInduk, @Email String email, String name, Integer age, String startYear,
		String endYear) {

}
