package com.tracker.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

}
