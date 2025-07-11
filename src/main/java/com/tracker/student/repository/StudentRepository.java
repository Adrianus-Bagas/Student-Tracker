package com.tracker.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
