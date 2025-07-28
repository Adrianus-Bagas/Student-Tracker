package com.tracker.student.constants;

public enum ResultTypes {
	TUGAS, KUIS, UTS, UAS;

	public static ResultTypes getType(final String input) {
		switch (input) {
		case "TUGAS":
			return TUGAS;
		case "KUIS":
			return KUIS;
		case "UTS":
			return UTS;
		case "UAS":
			return UAS;
		default:
			return null;
		}
	}
}
