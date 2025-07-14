package com.tracker.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	public Optional<Teacher> findBySecureId(String secureId);

	public void deleteBySecureId(String secureId);

}
