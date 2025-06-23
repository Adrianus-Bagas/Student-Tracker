package com.tracker.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.Result;

public interface StudentResultRepository extends JpaRepository<Result, Long> {

}
