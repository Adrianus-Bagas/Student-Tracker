package com.tracker.student.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

	private String filterKey;
	private String operation;
	private Object value;
	private String dataOption;

	public SearchCriteria(String filterKey, String operation, Object value) {
		super();
		this.filterKey = filterKey;
		this.operation = operation;
		this.value = value;
	}
}
