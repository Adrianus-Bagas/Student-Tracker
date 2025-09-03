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

import com.tracker.student.dto.request.CalculateFinalScoreRequestDTO;
import com.tracker.student.dto.request.CreateResultRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.dto.request.UpdateResultRequestDTO;
import com.tracker.student.dto.response.ClassDetailResponseDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.ResultDetailResponseDTO;
import com.tracker.student.dto.response.ResultListResponseDTO;
import com.tracker.student.dto.response.StudentDetailResponseDTO;
import com.tracker.student.dto.response.SubjectDetailResponseDTO;
import com.tracker.student.dto.response.SubjectDetailResultResponseDTO;
import com.tracker.student.dto.response.TeacherDetailResponseDTO;
import com.tracker.student.dto.response.UserInfoResponseDTO;
import com.tracker.student.entity.Result;
import com.tracker.student.entity.Student;
import com.tracker.student.entity.Subject;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.ResultRepository;
import com.tracker.student.repository.StudentRepository;
import com.tracker.student.repository.SubjectRepository;
import com.tracker.student.service.ResultService;
import com.tracker.student.specifications.builder.StudentResultSpecificationBuilder;
import com.tracker.student.util.AcademicYearChecker;
import com.tracker.student.util.CalculateFinalScore;
import com.tracker.student.util.PaginationUtil;

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

	@Override
	public ResultDetailResponseDTO findResultById(String id) {
		Result result = resultRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Nilai tidak ditemukan"));
		ResultDetailResponseDTO resultDTO = new ResultDetailResponseDTO();

		resultDTO.setId(result.getSecureId());
		resultDTO.setStartYear(result.getStartYear());
		resultDTO.setEndYear(result.getEndYear());
		resultDTO.setType(result.getType());
		resultDTO.setSemester(result.getSemester());
		resultDTO.setMark(result.getMark());
		resultDTO.setPassed(result.isPassed());

		SubjectDetailResponseDTO subjectDTO = new SubjectDetailResponseDTO();
		subjectDTO.setId(result.getSubject().getSecureId());
		subjectDTO.setStartYear(result.getSubject().getStartYear());
		subjectDTO.setEndYear(result.getSubject().getEndYear());
		subjectDTO.setName(result.getSubject().getName());
		subjectDTO.setMinimum(result.getSubject().getMinimum());

		TeacherDetailResponseDTO teacherDTO = new TeacherDetailResponseDTO();
		teacherDTO.setId(result.getSubject().getTeacher().getSecureId());
		teacherDTO.setStartYear(result.getSubject().getTeacher().getStartYear());
		teacherDTO.setEndYear(result.getSubject().getTeacher().getEndYear());

		UserInfoResponseDTO userDTO = new UserInfoResponseDTO();
		userDTO.setId(result.getSubject().getTeacher().getUser().getSecureId());
		userDTO.setStartYear(result.getSubject().getTeacher().getUser().getStartYear());
		userDTO.setEndYear(result.getSubject().getTeacher().getUser().getEndYear());
		userDTO.setNomorInduk(result.getSubject().getTeacher().getUser().getNomorInduk());
		userDTO.setName(result.getSubject().getTeacher().getUser().getName());
		userDTO.setEmail(result.getSubject().getTeacher().getUser().getEmail());
		userDTO.setAge(result.getSubject().getTeacher().getUser().getAge());
		userDTO.setRole(result.getSubject().getTeacher().getUser().getRole());

		teacherDTO.setUser(userDTO);

		subjectDTO.setTeacher(teacherDTO);

		resultDTO.setSubject(subjectDTO);

		StudentDetailResponseDTO studentDTO = new StudentDetailResponseDTO();
		studentDTO.setId(result.getStudent().getSecureId());
		studentDTO.setStartYear(result.getStudent().getStartYear());
		studentDTO.setEndYear(result.getStudent().getEndYear());
		studentDTO.setPromoted(result.getStudent().isPromoted());

		UserInfoResponseDTO studentUserInfoResponseDTO = new UserInfoResponseDTO();
		studentUserInfoResponseDTO.setAge(result.getStudent().getUser().getAge());
		studentUserInfoResponseDTO.setEmail(result.getStudent().getUser().getEmail());
		studentUserInfoResponseDTO.setEndYear(result.getStudent().getUser().getEndYear());
		studentUserInfoResponseDTO.setId(result.getStudent().getUser().getSecureId());
		studentUserInfoResponseDTO.setName(result.getStudent().getUser().getName());
		studentUserInfoResponseDTO.setNomorInduk(result.getStudent().getUser().getNomorInduk());
		studentUserInfoResponseDTO.setRole(result.getStudent().getUser().getRole());
		studentUserInfoResponseDTO.setStartYear(result.getStudent().getUser().getStartYear());

		ClassDetailResponseDTO classDetailResponseDTO = new ClassDetailResponseDTO();
		classDetailResponseDTO.setId(result.getStudent().getStudentClass().getSecureId());
		classDetailResponseDTO.setStartYear(result.getStudent().getStudentClass().getStartYear());
		classDetailResponseDTO.setEndYear(result.getStudent().getStudentClass().getEndYear());
		classDetailResponseDTO.setName(result.getStudent().getStudentClass().getName());

		studentDTO.setUser(studentUserInfoResponseDTO);
		studentDTO.setStudentClass(classDetailResponseDTO);

		resultDTO.setStudent(studentDTO);

		return resultDTO;
	}

	@Override
	public void updateResult(UpdateResultRequestDTO dto, String id) {
		Result result = resultRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("Nilai tidak ditemukan"));

		if (dto.startYear() != null && dto.endYear() != null) {
			AcademicYearChecker academicYearChecker = new AcademicYearChecker();
			academicYearChecker.checkAcademicYearValidity(dto.startYear(), dto.endYear());
		}
		try {
			if (dto.mark() > result.getSubject().getMinimum()) {
				result.setPassed(true);
			} else {
				result.setPassed(false);
			}
			if (dto.subjectId() != null) {
				Subject subject = subjectRepository.findById(dto.subjectId())
						.orElseThrow(() -> new BadRequestException("Pelajaran tidak tersedia"));
				result.setSubject(subject);
			}
			if (dto.studentId() != null) {
				Student student = studentRepository.findById(dto.studentId())
						.orElseThrow(() -> new BadRequestException("Murid tidak tersedia"));
				result.setStudent(student);
			}
			if (dto.startYear() != null)
				result.setStartYear(dto.startYear());
			if (dto.endYear() != null)
				result.setEndYear(dto.endYear());
			if (dto.mark() != null)
				result.setMark(dto.mark());
			if (dto.semester() != null)
				result.setSemester(dto.semester());
			if (dto.type() != null)
				result.setType(dto.type());
			resultRepository.save(result);
		} catch (Exception e) {
			logger.error("Failed to update result");
			throw new BadRequestException("Nilai tidak dapat diubah");
		}
	}

	@Override
	@Transactional
	public void deleteResult(String id) {
		resultRepository.deleteBySecureId(id);
	}

	@Override
	public PageResultResponseDTO<ResultListResponseDTO> findStudentResultList(int page, int limit, String sortBy,
			String direction, FilterSearchRequestDTO dto) {
		List<SearchCriteria> criteriaList = dto.searchCriteriaList();
		StudentResultSpecificationBuilder builder = new StudentResultSpecificationBuilder();
		if (criteriaList != null) {
			criteriaList.forEach(x -> {
				x.setDataOption(dto.dataOption());
				builder.with(x);
			});
		}
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<Result> pageResult = resultRepository.findAll(builder.build(), pageable);
		List<ResultListResponseDTO> dtos = pageResult.stream().map((result) -> {
			ResultListResponseDTO resultDto = new ResultListResponseDTO();
			resultDto.setId(result.getSecureId());
			resultDto.setStartYear(result.getStartYear());
			resultDto.setEndYear(result.getEndYear());
			resultDto.setSemester(result.getSemester());
			resultDto.setMark(result.getMark());
			resultDto.setType(result.getType());
			resultDto.setIsPassed(result.isPassed());

			SubjectDetailResultResponseDTO subjectDto = new SubjectDetailResultResponseDTO();
			subjectDto.setId(result.getSubject().getSecureId());
			subjectDto.setStartYear(result.getSubject().getStartYear());
			subjectDto.setEndYear(result.getSubject().getEndYear());
			subjectDto.setName(result.getSubject().getName());
			subjectDto.setMinimum(result.getSubject().getMinimum());

			resultDto.setSubject(subjectDto);
			return resultDto;
		}).collect(Collectors.toList());
		return PaginationUtil.createPageResultDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
	}

	@Override
	public Integer calculateFinalScore(CalculateFinalScoreRequestDTO dto) {
		CalculateFinalScore calculateFinalScore = new CalculateFinalScore();
		float finalScore = calculateFinalScore.getFinalScore(dto.taskScores(), dto.quizScores(), dto.midtermScores(),
				dto.finaltermScores());
		return Math.round(finalScore);
	}

}
