package com.tracker.student.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracker.student.dto.request.CreateClassRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.dto.request.UpdateClassRequestDTO;
import com.tracker.student.dto.response.ClassDetailResponseDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.entity.Class;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.ClassRepository;
import com.tracker.student.service.ClassService;
import com.tracker.student.specifications.builder.ClassSpecificationBuilder;
import com.tracker.student.util.AcademicYearChecker;
import com.tracker.student.util.PaginationUtil;

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

	@Override
	public ClassDetailResponseDTO findClassById(String id) {
		Class classExit = classRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Kelas tidak ditemukan"));
		ClassDetailResponseDTO dto = new ClassDetailResponseDTO();
		dto.setId(classExit.getSecureId());
		dto.setName(classExit.getName());
		dto.setStartYear(classExit.getStartYear());
		dto.setEndYear(classExit.getEndYear());
		return dto;
	}

	@Override
	public void updateClass(UpdateClassRequestDTO dto, String id) {
		Class existClass = classRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Kelas tidak ditemukan"));
		if (dto.startYear() != null && dto.endYear() != null) {
			AcademicYearChecker academicYearChecker = new AcademicYearChecker();
			academicYearChecker.checkAcademicYearValidity(dto.startYear(), dto.endYear());
		}
		try {
			if (dto.startYear() != null)
				existClass.setStartYear(dto.startYear());
			if (dto.endYear() != null)
				existClass.setEndYear(dto.endYear());
			if (dto.name() != null)
				existClass.setName(dto.name());
			classRepository.save(existClass);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BadRequestException("gagal update kelas");
		}
	}

	@Override
	@Transactional
	public void deleteClass(String id) {
		classRepository.deleteBySecureId(id);
	}

	@Override
	public PageResultResponseDTO<ClassDetailResponseDTO> findClassList(int page, int limit, String sortBy,
			String direction, FilterSearchRequestDTO dto) {
		List<SearchCriteria> criteriaList = dto.searchCriteriaList();
		ClassSpecificationBuilder builder = new ClassSpecificationBuilder();
		if (criteriaList != null) {
			criteriaList.forEach(x -> {
				x.setDataOption(dto.dataOption());
				builder.with(x);
			});
		}
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<Class> pageResult = classRepository.findAll(builder.build(), pageable);
		List<ClassDetailResponseDTO> dtos = pageResult.stream().map((existClass) -> {
			ClassDetailResponseDTO classDto = new ClassDetailResponseDTO();
			classDto.setId(existClass.getSecureId());
			classDto.setStartYear(existClass.getStartYear());
			classDto.setEndYear(existClass.getEndYear());
			classDto.setName(existClass.getName());
			return classDto;
		}).collect(Collectors.toList());
		return PaginationUtil.createPageResultDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
	}

}
