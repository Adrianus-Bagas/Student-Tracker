package com.tracker.student.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.request.CreateClassRequestDTO;
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
	public ResponseEntity<Void> createClass(@Valid @RequestBody CreateClassRequestDTO dto) {
		classService.createClass(dto);
		return ResponseEntity.created(URI.create("/class")).build();
	}

}
