package com.tracker.student.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.request.ChangePasswordFromProfileRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.UpdateUserRequestDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.UserInfoResponseDTO;
import com.tracker.student.dto.response.UserListResponseDTO;
import com.tracker.student.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@PreAuthorize("hasAuthority('TU')")
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@PostMapping("/v1/pagelist")
	public ResponseEntity<PageResultResponseDTO<UserListResponseDTO>> findUserList(
			@RequestParam(name = "pages", required = true, defaultValue = "0") int pages,
			@RequestParam(name = "limit", required = true, defaultValue = "10") int limit,
			@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
			@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
			@RequestBody FilterSearchRequestDTO dto) {
		return ResponseEntity.ok(userService.findUserList(pages, limit, sortBy, direction, dto));
	}

	@GetMapping("/v1/{id}")
	public ResponseEntity<UserInfoResponseDTO> findUserById(@PathVariable String id) {
		return ResponseEntity.ok(userService.findUserBySecureId(id));
	}

	@PutMapping("/v1/update/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequestDTO dto) {
		userService.updateUser(id, dto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/v1/delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/v1/change-password-from-profile")
	public ResponseEntity<Void> changePasswordFromProfile(@Valid @RequestBody ChangePasswordFromProfileRequestDTO dto) {
		userService.changePasswordFromProfile(dto);
		return ResponseEntity.created(URI.create("/change-password-from-profile")).build();
	}

}
