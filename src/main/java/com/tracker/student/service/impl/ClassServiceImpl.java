package com.tracker.student.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tracker.student.dto.request.CreateClassRequestDTO;
import com.tracker.student.entity.Class;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.ClassRepository;
import com.tracker.student.service.ClassService;
import com.tracker.student.util.AcademicYearChecker;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClassServiceImpl implements ClassService {

	private final ClassRepository classRepository;

	private static final Logger logger = LoggerFactory.getLogger(ClassServiceImpl.class);

	@Override
	public void createClass(CreateClassRequestDTO dto) {
		AcademicYearChecker academicYearChecker = new AcademicYearChecker();
		Class classObject = classRepository.findByName(dto.name()).orElse(new Class());
		if (!StringUtils.isBlank(classObject.getName())) {
			throw new BadRequestException("Kelas sudah tersedia");
		}
		academicYearChecker.checkAcademicYearValidity(dto.startYear(), dto.endYear());
		try {
			classObject.setName(dto.name());
			classObject.setStartYear(dto.startYear());
			classObject.setEndYear(dto.endYear());
			classRepository.save(classObject);
		} catch (Exception e) {
			logger.error("Failed to save class");
			throw new BadRequestException("Gagal menambahkan kelas");
		}
	}

}
