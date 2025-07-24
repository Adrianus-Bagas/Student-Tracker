package com.tracker.student.service;

import com.tracker.student.dto.request.CreateSubjectRequestDTO;
import com.tracker.student.dto.request.UpdateSubjectRequestDTO;
import com.tracker.student.dto.response.SubjectDetailResponseDTO;

public interface SubjectService {

	public void createSubject(CreateSubjectRequestDTO dto);

	public SubjectDetailResponseDTO findSubjectById(String id);

	public void updateSubject(UpdateSubjectRequestDTO dto, String id);

	public void deleteSubject(String id);

}
