package com.tracker.student.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PageResultResponseDTO<T> {

	private List<T> result;
	private int page;
	private Long elements;

}
