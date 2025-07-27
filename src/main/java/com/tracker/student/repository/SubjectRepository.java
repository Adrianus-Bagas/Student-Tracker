package com.tracker.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tracker.student.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

	public Optional<Subject> findByName(String name);

	public Optional<Subject> findBySecureId(String secureId);

	public void deleteBySecureId(String secureId);

}
