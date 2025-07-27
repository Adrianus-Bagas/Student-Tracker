package com.tracker.student.specifications.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tracker.student.constants.SearchOperation;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.entity.Class;
import com.tracker.student.specifications.ClassSpecification;

public class ClassSpecificationBuilder {
	private final List<SearchCriteria> params;

	public ClassSpecificationBuilder() {
		this.params = new ArrayList<>();
	}

	public final ClassSpecificationBuilder with(String key, String operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}

	public final ClassSpecificationBuilder with(SearchCriteria searchCriteria) {
		params.add(searchCriteria);
		return this;
	}

	public Specification<Class> build() {
		if (params.size() == 0) {
			return null;
		}

		Specification<Class> result = new ClassSpecification(params.get(0));
		for (int idx = 1; idx < params.size(); idx++) {
			SearchCriteria criteria = params.get(idx);
			result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
					? Specification.where(result).and(new ClassSpecification(criteria))
					: Specification.where(result).or(new ClassSpecification(criteria));
		}

		return result;
	}
}
