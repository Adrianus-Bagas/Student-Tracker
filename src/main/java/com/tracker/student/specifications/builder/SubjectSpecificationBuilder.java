package com.tracker.student.specifications.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tracker.student.constants.SearchOperation;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.entity.Subject;
import com.tracker.student.specifications.SubjectSpecification;

public class SubjectSpecificationBuilder {
	private final List<SearchCriteria> params;

	public SubjectSpecificationBuilder() {
		this.params = new ArrayList<>();
	}

	public final SubjectSpecificationBuilder with(String key, String operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}

	public final SubjectSpecificationBuilder with(SearchCriteria searchCriteria) {
		params.add(searchCriteria);
		return this;
	}

	public Specification<Subject> build() {
		if (params.size() == 0) {
			return null;
		}

		Specification<Subject> result = new SubjectSpecification(params.get(0));
		for (int idx = 1; idx < params.size(); idx++) {
			SearchCriteria criteria = params.get(idx);
			result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
					? Specification.where(result).and(new SubjectSpecification(criteria))
					: Specification.where(result).or(new SubjectSpecification(criteria));
		}

		return result;
	}
}
