package com.tracker.student.specifications;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.tracker.student.constants.SearchOperation;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.entity.Class;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ClassSpecification implements Specification<Class> {

	private final SearchCriteria searchCriteria;

	public ClassSpecification(final SearchCriteria searchCriteria) {
		super();
		this.searchCriteria = searchCriteria;
	}

	@Override
	@Nullable
	public Predicate toPredicate(Root<Class> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		String strToSearch = searchCriteria.getValue().toString().toLowerCase();

		switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
		case CONTAINS:
			return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					"%" + strToSearch + "%");

		case DOES_NOT_CONTAIN:
			return criteriaBuilder.notLike(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					"%" + strToSearch + "%");

		case BEGINS_WITH:
			return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					strToSearch + "%");

		case DOES_NOT_BEGIN_WITH:
			return criteriaBuilder.notLike(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					strToSearch + "%");

		case ENDS_WITH:
			return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					"%" + strToSearch);

		case DOES_NOT_END_WITH:
			return criteriaBuilder.notLike(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					"%" + strToSearch);

		case EQUAL:
			return criteriaBuilder.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());

		case NOT_EQUAL:
			return criteriaBuilder.notEqual(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());

		case NULL:
			return criteriaBuilder.isNull(root.get(searchCriteria.getFilterKey()));

		case NOT_NULL:
			return criteriaBuilder.isNotNull(root.get(searchCriteria.getFilterKey()));

		case GREATER_THAN:
			return criteriaBuilder.greaterThan(root.<String>get(searchCriteria.getFilterKey()),
					searchCriteria.getValue().toString());

		case GREATER_THAN_EQUAL:
			return criteriaBuilder.greaterThanOrEqualTo(root.<String>get(searchCriteria.getFilterKey()),
					searchCriteria.getValue().toString());

		case LESS_THAN:
			return criteriaBuilder.lessThan(root.<String>get(searchCriteria.getFilterKey()),
					searchCriteria.getValue().toString());

		case LESS_THAN_EQUAL:
			return criteriaBuilder.lessThanOrEqualTo(root.<String>get(searchCriteria.getFilterKey()),
					searchCriteria.getValue().toString());
		}
		return null;
	}

}
