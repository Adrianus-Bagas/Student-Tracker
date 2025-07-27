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

import com.tracker.student.dto.request.CreateSubjectRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.dto.request.UpdateSubjectRequestDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.SubjectDetailResponseDTO;
import com.tracker.student.dto.response.TeacherDetailResponseDTO;
import com.tracker.student.dto.response.UserInfoResponseDTO;
import com.tracker.student.entity.Subject;
import com.tracker.student.entity.Teacher;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.SubjectRepository;
import com.tracker.student.repository.TeacherRepository;
import com.tracker.student.service.SubjectService;
import com.tracker.student.specifications.builder.SubjectSpecificationBuilder;
import com.tracker.student.util.AcademicYearChecker;
import com.tracker.student.util.PaginationUtil;

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

	@Override
	public SubjectDetailResponseDTO findSubjectById(String id) {
		Subject subject = subjectRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Pelajaran tidak ditemukan"));
		SubjectDetailResponseDTO dto = new SubjectDetailResponseDTO();
		dto.setId(subject.getSecureId());
		dto.setStartYear(subject.getStartYear());
		dto.setEndYear(subject.getEndYear());
		dto.setName(subject.getName());
		dto.setMinimum(subject.getMinimum());

		TeacherDetailResponseDTO teacherDTO = new TeacherDetailResponseDTO();
		teacherDTO.setId(subject.getTeacher().getSecureId());
		teacherDTO.setStartYear(subject.getTeacher().getStartYear());
		teacherDTO.setEndYear(subject.getTeacher().getEndYear());

		UserInfoResponseDTO userDTO = new UserInfoResponseDTO();
		userDTO.setId(subject.getTeacher().getUser().getSecureId());
		userDTO.setStartYear(subject.getTeacher().getUser().getStartYear());
		userDTO.setEndYear(subject.getTeacher().getUser().getEndYear());
		userDTO.setNomorInduk(subject.getTeacher().getUser().getNomorInduk());
		userDTO.setName(subject.getTeacher().getUser().getName());
		userDTO.setEmail(subject.getTeacher().getUser().getEmail());
		userDTO.setAge(subject.getTeacher().getUser().getAge());
		userDTO.setRole(subject.getTeacher().getUser().getRole());

		teacherDTO.setUser(userDTO);

		dto.setTeacher(teacherDTO);
		return dto;
	}

	@Override
	public void updateSubject(UpdateSubjectRequestDTO dto, String id) {
		Subject subject = subjectRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Pelajaran tidak ditemukan"));

		if (dto.startYear() != null && dto.endYear() != null) {
			AcademicYearChecker academicYearChecker = new AcademicYearChecker();
			academicYearChecker.checkAcademicYearValidity(dto.startYear(), dto.endYear());
		}
		try {
			if (dto.name() != null)
				subject.setName(dto.name());
			if (dto.startYear() != null)
				subject.setStartYear(dto.startYear());
			if (dto.endYear() != null)
				subject.setEndYear(dto.endYear());
			if (dto.minimum() != 0)
				subject.setMinimum(dto.minimum());
			if (dto.teacherId() != null) {
				Teacher teacher = teacherRepository.findById(dto.teacherId())
						.orElseThrow(() -> new BadRequestException("Guru tidak tersedia"));
				subject.setTeacher(teacher);
			}
			subjectRepository.save(subject);
		} catch (Exception e) {
			logger.error("Failed to update subject");
			throw new BadRequestException("Gagal mengubah pelajaran");
		}

	}

	@Override
	@Transactional
	public void deleteSubject(String id) {
		subjectRepository.deleteBySecureId(id);
	}

	@Override
	public PageResultResponseDTO<SubjectDetailResponseDTO> findSubjectList(int page, int limit, String sortBy,
			String direction, FilterSearchRequestDTO dto) {
		List<SearchCriteria> criteriaList = dto.searchCriteriaList();
		SubjectSpecificationBuilder builder = new SubjectSpecificationBuilder();
		if (criteriaList != null) {
			criteriaList.forEach(x -> {
				x.setDataOption(dto.dataOption());
				builder.with(x);
			});
		}
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<Subject> pageResult = subjectRepository.findAll(builder.build(), pageable);
		List<SubjectDetailResponseDTO> dtos = pageResult.stream().map((subject) -> {
			SubjectDetailResponseDTO subjectDto = new SubjectDetailResponseDTO();
			subjectDto.setId(subject.getSecureId());
			subjectDto.setStartYear(subject.getStartYear());
			subjectDto.setEndYear(subject.getEndYear());
			subjectDto.setName(subject.getName());
			subjectDto.setMinimum(subject.getMinimum());

			TeacherDetailResponseDTO teacherDTO = new TeacherDetailResponseDTO();
			teacherDTO.setId(subject.getTeacher().getSecureId());
			teacherDTO.setStartYear(subject.getTeacher().getStartYear());
			teacherDTO.setEndYear(subject.getTeacher().getEndYear());

			UserInfoResponseDTO userDTO = new UserInfoResponseDTO();
			userDTO.setId(subject.getTeacher().getUser().getSecureId());
			userDTO.setStartYear(subject.getTeacher().getUser().getStartYear());
			userDTO.setEndYear(subject.getTeacher().getUser().getEndYear());
			userDTO.setNomorInduk(subject.getTeacher().getUser().getNomorInduk());
			userDTO.setName(subject.getTeacher().getUser().getName());
			userDTO.setEmail(subject.getTeacher().getUser().getEmail());
			userDTO.setAge(subject.getTeacher().getUser().getAge());
			userDTO.setRole(subject.getTeacher().getUser().getRole());

			teacherDTO.setUser(userDTO);

			subjectDto.setTeacher(teacherDTO);

			return subjectDto;
		}).collect(Collectors.toList());
		return PaginationUtil.createPageResultDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
	}

}
