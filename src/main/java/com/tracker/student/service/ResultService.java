package com.tracker.student.service;

import com.tracker.student.dto.request.CalculateFinalScoreRequestDTO;
import com.tracker.student.dto.request.CreateResultRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.UpdateResultRequestDTO;
import com.tracker.student.dto.response.CalculateFinalScoreResponseDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.ResultDetailResponseDTO;
import com.tracker.student.dto.response.ResultListResponseDTO;

public interface ResultService {

	public void createResult(CreateResultRequestDTO dto);

	public ResultDetailResponseDTO findResultById(String id);

	public void updateResult(UpdateResultRequestDTO dto, String id);

	public void deleteResult(String id);

	public PageResultResponseDTO<ResultListResponseDTO> findStudentResultList(int page, int limit, String sortBy,
			String direction, FilterSearchRequestDTO dto);

	public CalculateFinalScoreResponseDTO calculateFinalScore(CalculateFinalScoreRequestDTO dto);

}
