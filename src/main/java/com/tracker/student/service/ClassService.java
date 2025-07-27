package com.tracker.student.service;

import com.tracker.student.dto.request.CreateClassRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.UpdateClassRequestDTO;
import com.tracker.student.dto.response.ClassDetailResponseDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;

public interface ClassService {

	public void createClass(CreateClassRequestDTO dto);

	public ClassDetailResponseDTO findClassById(String id);

	public void updateClass(UpdateClassRequestDTO dto, String id);

	public void deleteClass(String id);

	public PageResultResponseDTO<ClassDetailResponseDTO> findClassList(int page, int limit, String sortBy,
			String direction, FilterSearchRequestDTO dto);

}
