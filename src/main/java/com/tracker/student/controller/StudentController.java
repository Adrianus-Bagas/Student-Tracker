package com.tracker.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.student.dto.request.UpdateStudentRequestDTO;
import com.tracker.student.dto.response.StudentDetailResponseDTO;
import com.tracker.student.service.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

	private final StudentService studentService;

	@GetMapping("/v1/detail/{id}")
	public ResponseEntity<StudentDetailResponseDTO> getStudentById(@PathVariable String id) {
		return ResponseEntity.ok(studentService.findStudentBySecureId(id));
	}

	@PutMapping("/v1/update/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable String id, @Valid @RequestBody UpdateStudentRequestDTO dto) {
		studentService.updateStudent(dto, id);
		return ResponseEntity.ok().build();
	}

}
