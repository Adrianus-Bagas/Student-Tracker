package com.tracker.student.dto.request;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank String nomorInduk, @NotBlank @Length(min = 8) String password) {

}
