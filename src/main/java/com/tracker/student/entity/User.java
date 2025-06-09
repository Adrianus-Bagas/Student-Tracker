package com.tracker.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "app_user", indexes = { @Index(name = "idx_nomor_induk_user", columnList = "nomor_induk") })
public class User extends AbstractBaseEntity {

	private static final long serialVersionUID = -504151175727503690L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq")
	private Long id;

	@Column(name = "nomor_induk", nullable = false)
	private String nomorInduk;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "age", nullable = false)
	private int age;

	@Column(name = "role", nullable = false)
	private String role;

}
