package com.tracker.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.Teacher;
import com.tracker.student.entity.Class;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	public Optional<Teacher> findBySecureId(String secureId);

	public Optional<Teacher> findByTeacherClass(Class teacherClass);

	public void deleteBySecureId(String secureId);

}
