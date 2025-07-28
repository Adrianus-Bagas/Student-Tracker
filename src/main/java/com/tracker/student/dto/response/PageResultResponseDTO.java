package com.tracker.student.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class PageResultResponseDTO<T> {

	private List<T> result;
	private int page;
	private Long elements;

}
