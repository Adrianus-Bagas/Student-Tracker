package com.tracker.student.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.request.CreateResultRequestDTO;
import com.tracker.student.dto.response.ResultDetailResponseDTO;
import com.tracker.student.service.ResultService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/result")
@AllArgsConstructor
@PreAuthorize("hasAuthority('TEACHER')")
public class ResultController {

	private final ResultService resultService;

	@PostMapping("/v1/create")
	public ResponseEntity<Void> createResult(@Valid @RequestBody CreateResultRequestDTO dto) {
		resultService.createResult(dto);
		return ResponseEntity.created(URI.create("/result")).build();
	}
	
	@GetMapping("/v1/detail/{id}")
	public ResponseEntity<ResultDetailResponseDTO> getDetailResult(@PathVariable String id) {
		return ResponseEntity.ok(resultService.findResultById(id));
	}

}
