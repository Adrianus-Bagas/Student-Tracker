package com.tracker.student.service;

import com.tracker.student.dto.response.StudentDetailResponseDTO;

public interface StudentService {

	public StudentDetailResponseDTO findStudentBySecureId(String id);

}
