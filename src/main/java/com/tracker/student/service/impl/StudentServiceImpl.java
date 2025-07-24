package com.tracker.student.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracker.student.dto.request.UpdateStudentRequestDTO;
import com.tracker.student.dto.response.ClassDetailResponseDTO;
import com.tracker.student.dto.response.StudentDetailResponseDTO;
import com.tracker.student.dto.response.UserInfoResponseDTO;
import com.tracker.student.entity.Class;
import com.tracker.student.entity.Student;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.ClassRepository;
import com.tracker.student.repository.StudentRepository;
import com.tracker.student.service.StudentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final ClassRepository classRepository;

	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Override
	public StudentDetailResponseDTO findStudentBySecureId(String id) {

		Student student = studentRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Siswa tidak ditemukan"));

		StudentDetailResponseDTO dto = new StudentDetailResponseDTO();

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

		dto.setId(student.getSecureId());
		dto.setStartYear(student.getStartYear());
		dto.setEndYear(student.getEndYear());
		dto.setPromoted(student.isPromoted());
		dto.setUser(userInfoResponseDTO);
		dto.setStudentClass(classDetailResponseDTO);

		return dto;
	}

	@Override
	public void updateStudent(UpdateStudentRequestDTO dto, String id) {
		Student student = studentRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Siswa tidak ditemukan"));
		try {

			if (dto.classId() != null) {
				Class studentClass = classRepository.findById(dto.classId())
						.orElseThrow(() -> new BadRequestException("Kelas tidak ditemukan"));
				student.setStudentClass(studentClass);
			}
			student.setPromoted(dto.isPromoted());
			studentRepository.save(student);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BadRequestException("gagal update siswa");
		}
	}

	@Override
	@Transactional
	public void deleteStudent(String id) {
		studentRepository.deleteBySecureId(id);
	}

}
