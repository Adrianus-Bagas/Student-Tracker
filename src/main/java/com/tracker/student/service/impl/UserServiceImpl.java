package com.tracker.student.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.UserListResponseDTO;
import com.tracker.student.entity.User;
import com.tracker.student.repository.UserRepository;
import com.tracker.student.service.UserService;
import com.tracker.student.util.PaginationUtil;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

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

}
