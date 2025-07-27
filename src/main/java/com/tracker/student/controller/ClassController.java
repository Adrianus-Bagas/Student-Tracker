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

import com.tracker.student.dto.request.CreateClassRequestDTO;
import com.tracker.student.dto.request.FilterSearchRequestDTO;
import com.tracker.student.dto.request.UpdateClassRequestDTO;
import com.tracker.student.dto.response.ClassDetailResponseDTO;
import com.tracker.student.dto.response.PageResultResponseDTO;
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

	@GetMapping("/v1/detail/{id}")
	public ResponseEntity<ClassDetailResponseDTO> getDetailClass(@PathVariable String id) {
		return ResponseEntity.ok(classService.findClassById(id));
	}

	@PutMapping("/v1/update/{id}")
	public ResponseEntity<Void> updateClass(@Valid @RequestBody UpdateClassRequestDTO dto, @PathVariable String id) {
		classService.updateClass(dto, id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/v1/delete/{id}")
	public ResponseEntity<Void> deleteClass(@PathVariable String id) {
		classService.deleteClass(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/v1/pagelist")
	public ResponseEntity<PageResultResponseDTO<ClassDetailResponseDTO>> findClassList(
			@RequestParam(name = "pages", required = true, defaultValue = "0") int pages,
			@RequestParam(name = "limit", required = true, defaultValue = "10") int limit,
			@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
			@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
			@RequestBody FilterSearchRequestDTO dto) {
		return ResponseEntity.ok(classService.findClassList(pages, limit, sortBy, direction, dto));
	}

}
