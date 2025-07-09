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

import com.tracker.student.dto.request.UpdateUserRequestDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.UserInfoResponseDTO;
import com.tracker.student.dto.response.UserListResponseDTO;
import com.tracker.student.entity.User;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.UserRepository;
import com.tracker.student.service.UserService;
import com.tracker.student.util.AcademicYearChecker;
import com.tracker.student.util.PaginationUtil;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User findByNomorInduk(String nomorInduk) {
		return userRepository.findByNomorInduk(nomorInduk).orElse(new User());
	}

	@Override
	public PageResultResponseDTO<UserListResponseDTO> findUserList(int page, int limit, String sortBy, String direction,
			String name) {
		name = StringUtils.isBlank(name) ? "%" : name + "%";
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<User> pageResult = userRepository.findByNameLikeIgnoreCase(name, pageable);
		List<UserListResponseDTO> dtos = pageResult.stream().map((user) -> {
			UserListResponseDTO dto = new UserListResponseDTO();
			dto.setUserId(user.getSecureId());
			dto.setRole(user.getRole());
			dto.setNomorInduk(user.getNomorInduk());
			dto.setName(user.getName());
			dto.setEmail(user.getEmail());
			return dto;
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

}
