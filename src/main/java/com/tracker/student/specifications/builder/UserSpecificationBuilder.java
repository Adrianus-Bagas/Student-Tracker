package com.tracker.student.specifications.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tracker.student.constants.SearchOperation;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.entity.User;
import com.tracker.student.specifications.UserSpecification;

public class UserSpecificationBuilder {
	private final List<SearchCriteria> params;

	public UserSpecificationBuilder() {
		this.params = new ArrayList<>();
	}

	public final UserSpecificationBuilder with(String key, String operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}

	public final UserSpecificationBuilder with(SearchCriteria searchCriteria) {
		params.add(searchCriteria);
		return this;
	}

	public Specification<User> build() {
		if (params.size() == 0) {
			return null;
		}

		Specification<User> result = new UserSpecification(params.get(0));
		for (int idx = 1; idx < params.size(); idx++) {
			SearchCriteria criteria = params.get(idx);
			result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
					? Specification.where(result).and(new UserSpecification(criteria))
					: Specification.where(result).or(new UserSpecification(criteria));
		}

		return result;
	}
}
