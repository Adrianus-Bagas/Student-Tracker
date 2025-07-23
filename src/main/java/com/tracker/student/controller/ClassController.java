package com.tracker.student.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.request.CreateUpdateClassRequestDTO;
import com.tracker.student.dto.response.ClassDetailResponseDTO;
import com.tracker.student.service.ClassService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/class")
@AllArgsConstructor
@PreAuthorize("hasAuthority('TU')")
public class ClassController {

	private final ClassService classService;

	@PostMapping("/v1/create")
	public ResponseEntity<Void> createClass(@Valid @RequestBody CreateUpdateClassRequestDTO dto) {
		classService.createClass(dto);
		return ResponseEntity.created(URI.create("/class")).build();
	}

	@GetMapping("/v1/detail/{id}")
	public ResponseEntity<ClassDetailResponseDTO> getDetailClass(@PathVariable String id) {
		return ResponseEntity.ok(classService.findClassById(id));
	}

	@PutMapping("/v1/update/{id}")
	public ResponseEntity<Void> updateClass(@Valid @RequestBody CreateUpdateClassRequestDTO dto,
			@PathVariable String id) {
		classService.updateClass(dto, id);
		return ResponseEntity.ok().build();
	}

}
