package com.tracker.student.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tracker.student.dto.request.CreateSubjectRequestDTO;
import com.tracker.student.entity.Subject;
import com.tracker.student.entity.Teacher;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.SubjectRepository;
import com.tracker.student.repository.TeacherRepository;
import com.tracker.student.service.SubjectService;
import com.tracker.student.util.AcademicYearChecker;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

	private final TeacherRepository teacherRepository;
	private final SubjectRepository subjectRepository;

	private static final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

	@Override
	public void createSubject(CreateSubjectRequestDTO dto) {
		AcademicYearChecker academicYearChecker = new AcademicYearChecker();
		Teacher teacher = teacherRepository.findById(dto.teacherId())
				.orElseThrow(() -> new BadRequestException("Guru tidak tersedia"));
		Subject subject = subjectRepository.findByName(dto.name()).orElse(new Subject());
		academicYearChecker.checkAcademicYearValidity(dto.startYear(), dto.endYear());
		if (!StringUtils.isBlank(subject.getName())) {
			throw new BadRequestException("Pelajaran sudah tersedia");
		}
		try {
			subject.setName(dto.name());
			subject.setStartYear(dto.startYear());
			subject.setEndYear(dto.endYear());
			subject.setMinimum(dto.minimum());
			subject.setTeacher(teacher);
			subjectRepository.save(subject);
		} catch (Exception e) {
			logger.error("Failed to save subject");
			throw new BadRequestException("Gagal menambahkan pelajaran");
		}
	}

}
