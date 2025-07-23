package com.tracker.student.service;

import com.tracker.student.dto.request.CreateClassRequestDTO;
import com.tracker.student.dto.response.ClassDetailResponseDTO;

public interface ClassService {

	public void createClass(CreateClassRequestDTO dto);

	public ClassDetailResponseDTO findClassById(String id);

}
