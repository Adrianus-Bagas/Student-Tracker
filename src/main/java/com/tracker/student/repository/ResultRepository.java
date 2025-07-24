package com.tracker.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

	public Optional<Result> findBySecureId(String secureId);

	public void deleteBySecureId(String secureId);

}
