package com.tracker.student.constants;

public enum SearchOperation {
	CONTAINS, DOES_NOT_CONTAIN, EQUAL, NOT_EQUAL, BEGINS_WITH, DOES_NOT_BEGIN_WITH, ENDS_WITH, DOES_NOT_END_WITH, NULL,
	NOT_NULL, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN, LESS_THAN_EQUAL, ANY, ALL;

	public static SearchOperation getDataOption(final String dataOption) {
		switch (dataOption) {
		case "ALL":
			return ALL;
		case "ANY":
			return ANY;
		default:
			return null;
		}
	}

	public static SearchOperation getSimpleOperation(final String input) {
		switch (input) {
		case "CONTAINS":
			return CONTAINS;
		case "DOES_NOT_CONTAIN":
			return DOES_NOT_CONTAIN;
		case "EQUAL":
			return EQUAL;
		case "NOT_EQUAL":
			return NOT_EQUAL;
		case "BEGINS_WITH":
			return BEGINS_WITH;
		case "DOES_NOT_BEGIN_WITH":
			return DOES_NOT_BEGIN_WITH;
		case "ENDS_WITH":
			return ENDS_WITH;
		case "DOES_NOT_END_WITH":
			return DOES_NOT_END_WITH;
		case "NULL":
			return NULL;
		case "NOT_NULL":
			return NOT_NULL;
		case "GREATER_THAN":
			return GREATER_THAN;
		case "GREATER_THAN_EQUAL":
			return GREATER_THAN_EQUAL;
		case "LESS_THAN":
			return LESS_THAN;
		case "LESS_THAN_EQUAL":
			return LESS_THAN_EQUAL;

		default:
			return null;
		}
	}
}
