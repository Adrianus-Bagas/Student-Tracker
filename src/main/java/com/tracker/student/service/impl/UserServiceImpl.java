package com.tracker.student.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracker.student.dto.request.ChangePasswordFromProfileRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.SearchCriteria;
import com.tracker.student.dto.request.UpdateUserRequestDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.UserInfoResponseDTO;
import com.tracker.student.dto.response.UserListResponseDTO;
import com.tracker.student.entity.User;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.UserRepository;
import com.tracker.student.service.UserService;
import com.tracker.student.specifications.builder.UserSpecificationBuilder;
import com.tracker.student.util.AcademicYearChecker;
import com.tracker.student.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User findByNomorInduk(String nomorInduk) {
		return userRepository.findByNomorInduk(nomorInduk).orElse(new User());
	}

	@Override
	public PageResultResponseDTO<UserListResponseDTO> findUserList(int page, int limit, String sortBy, String direction,
			FilterSearchRequestDTO dto) {
		List<SearchCriteria> criteriaList = dto.searchCriteriaList();
		UserSpecificationBuilder builder = new UserSpecificationBuilder();
		if (criteriaList != null) {
			criteriaList.forEach(x -> {
				x.setDataOption(dto.dataOption());
				builder.with(x);
			});
		}
		logger.info(dto.dataOption());
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<User> pageResult = userRepository.findAll(builder.build(), pageable);
		List<UserListResponseDTO> dtos = pageResult.stream().map((user) -> {
			UserListResponseDTO userDto = new UserListResponseDTO();
			userDto.setUserId(user.getSecureId());
			userDto.setRole(user.getRole());
			userDto.setNomorInduk(user.getNomorInduk());
			userDto.setName(user.getName());
			userDto.setEmail(user.getEmail());
			return userDto;
		}).collect(Collectors.toList());
		return PaginationUtil.createPageResultDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
	}

	@Override
	public UserInfoResponseDTO findUserBySecureId(String secureId) {
		User user = userRepository.findBySecureId(secureId)
				.orElseThrow(() -> new BadRequestException("User tidak ditemukan"));
		UserInfoResponseDTO dto = new UserInfoResponseDTO();
		dto.setAge(user.getAge());
		dto.setEmail(user.getEmail());
		dto.setEndYear(user.getEndYear());
		dto.setName(user.getName());
		dto.setNomorInduk(user.getNomorInduk());
		dto.setRole(user.getRole());
		dto.setId(user.getSecureId());
		dto.setStartYear(user.getStartYear());
		return dto;
	}

	@Override
	public void updateUser(String id, UpdateUserRequestDTO dto) {
		AcademicYearChecker academicYearChecker = new AcademicYearChecker();
		User user = userRepository.findBySecureId(id)
				.orElseThrow(() -> new BadRequestException("User tidak ditemukan"));
		academicYearChecker.checkAcademicYearValidity(dto.startYear(), dto.endYear());
		try {
			user.setEmail(dto.email());
			user.setName(dto.name());
			user.setAge(dto.age());
			user.setStartYear(dto.startYear());
			user.setEndYear(dto.endYear());
			user.setNomorInduk(dto.nomorInduk());
			userRepository.save(user);
		} catch (Exception e) {
			logger.error("Failed to update user");
			throw new BadRequestException("gagal update user");
		}
	}

	@Override
	@Transactional
	public void deleteUser(String id) {
		userRepository.deleteBySecureId(id);
	}

	@Override
	public void changePasswordFromProfile(ChangePasswordFromProfileRequestDTO dto) {
		User user = userRepository.findByNomorInduk(dto.nomorInduk())
				.orElseThrow(() -> new BadRequestException("User tidak Ditemukan"));
		if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
			throw new BadRequestException("Password lama tidak sesuai");
		}
		if (!dto.newPassword().matches(dto.confirmNewPassword())) {
			throw new BadRequestException("Mohon password baru dan konfirmasi password baru disamakan");
		}
		user.setPassword(passwordEncoder.encode(dto.newPassword()));
		userRepository.save(user);
	}

}
