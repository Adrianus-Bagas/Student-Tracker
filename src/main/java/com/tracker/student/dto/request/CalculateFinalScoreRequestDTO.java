package com.tracker.student.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record CalculateFinalScoreRequestDTO(@NotNull List<Float> taskScores, @NotNull List<Float> quizScores,
		@NotNull List<Float> midtermScores, @NotNull List<Float> finaltermScores) {

}
