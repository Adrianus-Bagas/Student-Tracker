package com.tracker.student.util;

import com.tracker.student.exception.BadRequestException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AcademicYearChecker {

	public void checkAcademicYearValidity(String startYear, String endYear) {
		Boolean isStartYearBiggerThanEqualToEndYear = Integer.parseInt(startYear) >= Integer.parseInt(endYear);
		Boolean isYearDifferenceMoreThanOne = Integer.parseInt(endYear) - Integer.parseInt(startYear) > 1;
		if (isStartYearBiggerThanEqualToEndYear || isYearDifferenceMoreThanOne) {
			throw new BadRequestException("Tahun Ajaran tidak Valid");
		}
	}

}
