package com.tracker.student.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.student.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByNomorInduk(String nomorInduk);

	public Optional<User> findByEmail(String email);

	public Optional<User> findByForgotPasswordCode(String forgotPasswordCode);

	public Page<User> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
