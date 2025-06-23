package com.tracker.student.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.request.CreateSubjectRequestDTO;
import com.tracker.student.service.SubjectService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/subject")
@AllArgsConstructor
@PreAuthorize("hasAuthority('TU')")
public class SubjectController {

	private final SubjectService subjectService;

	@PostMapping
	public ResponseEntity<Void> createSubject(@Valid @RequestBody CreateSubjectRequestDTO dto) {
		subjectService.createSubject(dto);
		return ResponseEntity.created(URI.create("/subject")).build();
	}

}
