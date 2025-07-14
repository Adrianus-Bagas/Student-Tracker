package com.tracker.student.service.impl;

import org.springframework.stereotype.Service;

import com.tracker.student.dto.response.ClassDetailResponseDTO;
import com.tracker.student.dto.response.StudentDetailResponseDTO;
import com.tracker.student.dto.response.TeacherDetailResponseDTO;
import com.tracker.student.dto.response.UserInfoResponseDTO;
import com.tracker.student.entity.Student;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.StudentRepository;
import com.tracker.student.service.StudentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	@Override
	public StudentDetailResponseDTO findStudentBySecureId(String id) {

		Student student = studentRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Siswa tidak ditemukan"));
		UserInfoResponseDTO userInfoResponseDTO = new UserInfoResponseDTO();
		userInfoResponseDTO.setAge(student.getUser().getAge());
		userInfoResponseDTO.setEmail(student.getUser().getEmail());
		userInfoResponseDTO.setEndYear(student.getUser().getEndYear());
		userInfoResponseDTO.setId(student.getUser().getSecureId());
		userInfoResponseDTO.setName(student.getUser().getName());
		userInfoResponseDTO.setNomorInduk(student.getUser().getNomorInduk());
		userInfoResponseDTO.setRole(student.getUser().getRole());
		userInfoResponseDTO.setStartYear(student.getUser().getStartYear());

		ClassDetailResponseDTO classDetailResponseDTO = new ClassDetailResponseDTO();
		classDetailResponseDTO.setId(student.getStudentClass().getSecureId());
		classDetailResponseDTO.setStartYear(student.getStudentClass().getStartYear());
		classDetailResponseDTO.setEndYear(student.getStudentClass().getEndYear());
		classDetailResponseDTO.setName(student.getStudentClass().getName());

		TeacherDetailResponseDTO teacherDetailResponseDTO = new TeacherDetailResponseDTO();
		teacherDetailResponseDTO.setId(student.getTeacher().getSecureId());
		teacherDetailResponseDTO.setStartYear(student.getTeacher().getStartYear());
		teacherDetailResponseDTO.setEndYear(student.getTeacher().getEndYear());

		UserInfoResponseDTO teacheInfoResponseDTO = new UserInfoResponseDTO();
		teacheInfoResponseDTO.setAge(student.getTeacher().getUser().getAge());
		teacheInfoResponseDTO.setEmail(student.getTeacher().getUser().getEmail());
		teacheInfoResponseDTO.setEndYear(student.getTeacher().getUser().getEndYear());
		teacheInfoResponseDTO.setId(student.getTeacher().getUser().getSecureId());
		teacheInfoResponseDTO.setName(student.getTeacher().getUser().getName());
		teacheInfoResponseDTO.setNomorInduk(student.getTeacher().getUser().getNomorInduk());
		teacheInfoResponseDTO.setRole(student.getTeacher().getUser().getRole());
		teacheInfoResponseDTO.setStartYear(student.getTeacher().getUser().getStartYear());

		teacherDetailResponseDTO.setUser(teacheInfoResponseDTO);

		classDetailResponseDTO.setTeacher(teacherDetailResponseDTO);

		return new StudentDetailResponseDTO(student.getSecureId(), student.getStartYear(), student.getEndYear(),
				student.isPromoted(), userInfoResponseDTO, classDetailResponseDTO);
	}

}
