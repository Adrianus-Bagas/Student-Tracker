package com.tracker.student.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.request.CreateResultRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.UpdateResultRequestDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
import com.tracker.student.dto.response.ResultDetailResponseDTO;
import com.tracker.student.dto.response.ResultListResponseDTO;
import com.tracker.student.service.ResultService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/result")
@AllArgsConstructor
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

	@PutMapping("/v1/update/{id}")
	public ResponseEntity<Void> updateResult(@Valid @RequestBody UpdateResultRequestDTO dto, @PathVariable String id) {
		resultService.updateResult(dto, id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/v1/delete/{id}")
	public ResponseEntity<Void> deleteResult(@PathVariable String id) {
		resultService.deleteResult(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/v1/pagelist")
	public ResponseEntity<PageResultResponseDTO<ResultListResponseDTO>> findUserList(
			@RequestParam(name = "pages", required = true, defaultValue = "0") int pages,
			@RequestParam(name = "limit", required = true, defaultValue = "10") int limit,
			@RequestParam(name = "sortBy", required = true, defaultValue = "mark") String sortBy,
			@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
			@RequestBody FilterSearchRequestDTO dto) {
		return ResponseEntity.ok(resultService.findStudentResultList(pages, limit, sortBy, direction, dto));
	}

}
