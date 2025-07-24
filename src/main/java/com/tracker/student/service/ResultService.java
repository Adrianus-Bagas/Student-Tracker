package com.tracker.student.service;

import com.tracker.student.dto.request.CreateResultRequestDTO;
import com.tracker.student.dto.request.UpdateResultRequestDTO;
import com.tracker.student.dto.response.ResultDetailResponseDTO;

public interface ResultService {

	public void createResult(CreateResultRequestDTO dto);

	public ResultDetailResponseDTO findResultById(String id);

	public void updateResult(UpdateResultRequestDTO dto, String id);

}
