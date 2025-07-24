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
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.request.CreateSubjectRequestDTO;
import com.tracker.student.dto.request.UpdateSubjectRequestDTO;
import com.tracker.student.dto.response.SubjectDetailResponseDTO;
import com.tracker.student.service.SubjectService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/subject")
@AllArgsConstructor
@PreAuthorize("hasAuthority('TU')")
public class SubjectController {

	private final SubjectService subjectService;

	@PostMapping("/v1/create")
	public ResponseEntity<Void> createSubject(@Valid @RequestBody CreateSubjectRequestDTO dto) {
		subjectService.createSubject(dto);
		return ResponseEntity.created(URI.create("/subject")).build();
	}

	@GetMapping("/v1/detail/{id}")
	public ResponseEntity<SubjectDetailResponseDTO> getDetailSubject(@PathVariable String id) {
		return ResponseEntity.ok(subjectService.findSubjectById(id));
	}

	@PutMapping("/v1/update/{id}")
	public ResponseEntity<Void> updateSubject(@Valid @RequestBody UpdateSubjectRequestDTO dto,
			@PathVariable String id) {
		subjectService.updateSubject(dto, id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/v1/delete/{id}")
	public ResponseEntity<Void> deleteSubject(@PathVariable String id) {
		subjectService.deleteSubject(id);
		return ResponseEntity.ok().build();
	}

}
