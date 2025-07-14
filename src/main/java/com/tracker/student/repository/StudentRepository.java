package com.tracker.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	public Optional<Student> findBySecureId(String secureId);
	
	public void deleteBySecureId(String id);

}
