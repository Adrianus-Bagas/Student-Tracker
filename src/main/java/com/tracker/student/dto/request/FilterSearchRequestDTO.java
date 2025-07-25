package com.tracker.student.dto.request;

import java.util.List;

public record FilterSearchRequestDTO(List<SearchCriteria> searchCriteriaList, String dataOption) {

}
