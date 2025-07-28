package com.tracker.student.constants;

public enum Roles {
	TU, STUDENT, TEACHER;

	public static Roles getType(final String input) {
		switch (input) {
		case "TU":
			return TU;
		case "STUDENT":
			return STUDENT;
		case "TEACHER":
			return TEACHER;
		default:
			return null;
		}
	}
}
