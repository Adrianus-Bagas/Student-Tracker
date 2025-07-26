package com.tracker.student.specifications.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tracker.student.constants.SearchOperation;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.entity.Result;
import com.tracker.student.specifications.StudentResultSpecification;

public class StudentResultSpecificationBuilder {
	private final List<SearchCriteria> params;

	public StudentResultSpecificationBuilder() {
		this.params = new ArrayList<>();
	}

	public final StudentResultSpecificationBuilder with(String key, String operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}

	public final StudentResultSpecificationBuilder with(SearchCriteria searchCriteria) {
		params.add(searchCriteria);
		return this;
	}

	public Specification<Result> build() {
		if (params.size() == 0) {
			return null;
		}

		Specification<Result> result = new StudentResultSpecification(params.get(0));
		for (int idx = 1; idx < params.size(); idx++) {
			SearchCriteria criteria = params.get(idx);
			result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
					? Specification.where(result).and(new StudentResultSpecification(criteria))
					: Specification.where(result).or(new StudentResultSpecification(criteria));
		}

		return result;
	}
}
