package com.tracker.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tracker.student.entity.Class;

public interface ClassRepository extends JpaRepository<Class, Long> {

	public Optional<Class> findByName(String name);

}
