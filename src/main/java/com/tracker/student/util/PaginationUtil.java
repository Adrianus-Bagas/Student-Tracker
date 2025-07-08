package com.tracker.student.util;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.tracker.student.dto.response.PageResultResponseDTO;

public class PaginationUtil {

	public static Sort.Direction getSortBy(String sortBy) {
		if (sortBy.equalsIgnoreCase("asc")) {
			return Sort.Direction.ASC;
		} else {
			return Sort.Direction.DESC;
		}
	}

	public static <T> PageResultResponseDTO<T> createPageResultDTO(List<T> result, Long totalElements, int pages) {
		PageResultResponseDTO<T> dtos = new PageResultResponseDTO<T>();
		dtos.setPage(pages);
		dtos.setElements(totalElements);
		dtos.setResult(result);
		return dtos;
	}

}
