package com.tracker.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.response.TeacherDetailResponseDTO;
import com.tracker.student.service.TeacherService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/teacher")
@AllArgsConstructor
public class TeacherController {

	private final TeacherService teacherService;

	@GetMapping("/v1/detail/{id}")
	public ResponseEntity<TeacherDetailResponseDTO> getTeacherById(@PathVariable String id) {
		return ResponseEntity.ok(teacherService.findTeacherBySecureId(id));
	}

}
