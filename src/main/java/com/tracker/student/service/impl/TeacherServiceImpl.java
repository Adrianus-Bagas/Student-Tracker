package com.tracker.student.service.impl;

import org.springframework.stereotype.Service;

import com.tracker.student.dto.response.TeacherDetailResponseDTO;
import com.tracker.student.dto.response.UserInfoResponseDTO;
import com.tracker.student.entity.Teacher;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.TeacherRepository;
import com.tracker.student.service.TeacherService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository teacherRepository;

	@Override
	public TeacherDetailResponseDTO findTeacherBySecureId(String id) {
		Teacher teacher = teacherRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Guru tidak ditemukan"));

		TeacherDetailResponseDTO teacherDTO = new TeacherDetailResponseDTO();
		UserInfoResponseDTO userDTO = new UserInfoResponseDTO();

		userDTO.setId(teacher.getUser().getSecureId());
		userDTO.setStartYear(teacher.getUser().getStartYear());
		userDTO.setEndYear(teacher.getUser().getEndYear());
		userDTO.setNomorInduk(teacher.getUser().getNomorInduk());
		userDTO.setEmail(teacher.getUser().getEmail());
		userDTO.setAge(teacher.getUser().getAge());
		userDTO.setName(teacher.getUser().getName());
		userDTO.setRole(teacher.getUser().getRole());

		teacherDTO.setId(teacher.getSecureId());
		teacherDTO.setStartYear(teacher.getStartYear());
		teacherDTO.setEndYear(teacher.getEndYear());
		teacherDTO.setUser(userDTO);

		return teacherDTO;
	}

}
