package com.tracker.student.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tracker.student.dto.request.CreateResultRequestDTO;
import com.tracker.student.entity.Result;
import com.tracker.student.entity.Student;
import com.tracker.student.entity.Subject;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.ResultRepository;
import com.tracker.student.repository.StudentRepository;
import com.tracker.student.repository.SubjectRepository;
import com.tracker.student.service.ResultService;
import com.tracker.student.util.AcademicYearChecker;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {

	private final SubjectRepository subjectRepository;
	private final StudentRepository studentRepository;
	private final ResultRepository resultRepository;

	private static final Logger logger = LoggerFactory.getLogger(ResultServiceImpl.class);

	@Override
	public void createResult(CreateResultRequestDTO dto) {
		Subject subject = subjectRepository.findById(dto.subjectId())
				.orElseThrow(() -> new BadRequestException("Pelajaran tidak tersedia"));
		Student student = studentRepository.findById(dto.studentId())
				.orElseThrow(() -> new BadRequestException("Murid tidak tersedia"));
		AcademicYearChecker academicYearChecker = new AcademicYearChecker();
		academicYearChecker.checkAcademicYearValidity(dto.startYear(), dto.endYear());

		Result result = new Result();
		try {
			result.setStartYear(dto.startYear());
			result.setEndYear(dto.endYear());
			result.setMark(dto.mark());
			result.setSemester(dto.semester());
			result.setType(dto.type());
			result.setSubject(subject);
			result.setStudent(student);
			if (dto.mark() > subject.getMinimum()) {
				result.setPassed(true);
			} else {
				result.setPassed(false);
			}
			resultRepository.save(result);
		} catch (Exception e) {
			logger.error("Failed to save result");
			throw new BadRequestException("Nilai tidak dapat ditambahkan");
		}
	}

}
