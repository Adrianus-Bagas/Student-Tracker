package com.tracker.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
