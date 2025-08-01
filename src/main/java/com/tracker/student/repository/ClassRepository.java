package com.tracker.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tracker.student.entity.Class;

public interface ClassRepository extends JpaRepository<Class, Long>, JpaSpecificationExecutor<Class> {

	public Optional<Class> findByName(String name);

	public Optional<Class> findBySecureId(String secureId);

	public void deleteBySecureId(String secureId);

}
