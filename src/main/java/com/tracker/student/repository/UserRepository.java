package com.tracker.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByNomorInduk(String nomorInduk);
}
