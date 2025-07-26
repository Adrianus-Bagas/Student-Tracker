package com.tracker.student.specifications;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.tracker.student.constants.SearchOperation;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.entity.Result;
import com.tracker.student.entity.Subject;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StudentResultSpecification implements Specification<Result> {

	private final SearchCriteria searchCriteria;

	public StudentResultSpecification(final SearchCriteria searchCriteria) {
		super();
		this.searchCriteria = searchCriteria;
	}

	@Override
	@Nullable
	public Predicate toPredicate(Root<Result> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		String strToSearch = searchCriteria.getValue().toString().toLowerCase();

		switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
		case CONTAINS:
			if (searchCriteria.getFilterKey().equals("subjectId")) {
				return criteriaBuilder.like(criteriaBuilder.lower(subjectJoin(root).<String>get("secureId")),
						"%" + strToSearch + "%");
			}
			return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					"%" + strToSearch + "%");

		case DOES_NOT_CONTAIN:
			if (searchCriteria.getFilterKey().equals("subjectId")) {
				return criteriaBuilder.notLike(criteriaBuilder.lower(subjectJoin(root).<String>get("secureId")),
						"%" + strToSearch + "%");
			}
			return criteriaBuilder.notLike(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					"%" + strToSearch + "%");

		case BEGINS_WITH:
			if (searchCriteria.getFilterKey().equals("subjectId")) {
				return criteriaBuilder.like(criteriaBuilder.lower(subjectJoin(root).<String>get("secureId")),
						strToSearch + "%");
			}
			return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					strToSearch + "%");

		case DOES_NOT_BEGIN_WITH:
			if (searchCriteria.getFilterKey().equals("subjectId")) {
				return criteriaBuilder.notLike(criteriaBuilder.lower(subjectJoin(root).<String>get("secureId")),
						strToSearch + "%");
			}
			return criteriaBuilder.notLike(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					strToSearch + "%");

		case ENDS_WITH:
			if (searchCriteria.getFilterKey().equals("subjectId")) {
				return criteriaBuilder.like(criteriaBuilder.lower(subjectJoin(root).<String>get("secureId")),
						"%" + strToSearch);
			}
			return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					"%" + strToSearch);

		case DOES_NOT_END_WITH:
			if (searchCriteria.getFilterKey().equals("subjectId")) {
				return criteriaBuilder.notLike(criteriaBuilder.lower(subjectJoin(root).<String>get("secureId")),
						"%" + strToSearch);
			}
			return criteriaBuilder.notLike(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
					"%" + strToSearch);

		case EQUAL:
			if (searchCriteria.getFilterKey().equals("subjectId")) {
				return criteriaBuilder.equal(subjectJoin(root).<String>get("secureId"), searchCriteria.getValue());
			}
			if (searchCriteria.getFilterKey().equals("studentId")) {
				return criteriaBuilder.equal(studentJoin(root).<String>get("secureId"), searchCriteria.getValue());
			}
			return criteriaBuilder.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());

		case NOT_EQUAL:
			if (searchCriteria.getFilterKey().equals("subjectId")) {
				return criteriaBuilder.notEqual(subjectJoin(root).<String>get("secureId"), searchCriteria.getValue());
			}
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

	private Join<Result, Subject> subjectJoin(Root<Result> root) {
		return root.join("subject");
	}

	private Join<Result, Subject> studentJoin(Root<Result> root) {
		return root.join("student");
	}

}
