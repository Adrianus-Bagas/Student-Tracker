package com.tracker.student.service;

import com.tracker.student.dto.request.CreateUpdateClassRequestDTO;
import com.tracker.student.dto.response.ClassDetailResponseDTO;

public interface ClassService {

	public void createClass(CreateUpdateClassRequestDTO dto);

	public ClassDetailResponseDTO findClassById(String id);

	public void updateClass(CreateUpdateClassRequestDTO dto, String id);

	public void deleteClass(String id);

}
