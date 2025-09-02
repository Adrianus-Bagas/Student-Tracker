package com.tracker.student.util;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalculateFinalScore {

	public float getFinalScore(List<Float> taskScores, List<Float> quizScores, List<Float> midtermScore,
			List<Float> finaltermScore) {
//		Bobot tugas = 15%
//		Bobot kuis = 25%
//		Bobot UTS = 30%
//		Bobot UAS = 30%
		float overallTaskScore = taskScores.stream().reduce((float) 0, Float::sum);
		float overallQuizScore = quizScores.stream().reduce((float) 0, Float::sum);
		float overallMidtermScore = midtermScore.stream().reduce((float) 0, Float::sum);
		float overallFinalTermScore = finaltermScore.stream().reduce((float) 0, Float::sum);

		float finalScore = (overallTaskScore / taskScores.size() * 15 + overallQuizScore / quizScores.size() * 25
				+ overallMidtermScore / midtermScore.size() * 30 + overallFinalTermScore / finaltermScore.size() * 30)
				/ 100;
		return finalScore;
	}

}
