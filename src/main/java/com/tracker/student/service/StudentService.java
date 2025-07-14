package com.tracker.student.service;

import com.tracker.student.dto.request.UpdateStudentRequestDTO;
import com.tracker.student.dto.response.StudentDetailResponseDTO;

public interface StudentService {

	public StudentDetailResponseDTO findStudentBySecureId(String id);

	public void updateStudent(UpdateStudentRequestDTO dto, String id);
	
	public void deleteStudent(String id);

}
