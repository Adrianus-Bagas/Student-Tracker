package com.tracker.student.service;

import com.tracker.student.dto.response.TeacherDetailResponseDTO;

public interface TeacherService {

	public TeacherDetailResponseDTO findTeacherBySecureId(String id);

}
