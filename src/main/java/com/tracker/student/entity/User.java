package com.tracker.student.entity;

import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.tracker.student.constants.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "app_user", indexes = { @Index(name = "idx_nomor_induk_user", columnList = "nomor_induk") })
@SQLDelete(sql = "UPDATE app_user set is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted=false")
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

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Roles role;

	@Column(name = "forgot_password_code", nullable = true, unique = true)
	private String forgotPasswordCode;

	@Column(name = "forgot_password_code_expired_at", nullable = true)
	private Date forgotPasswordCodeExpiredAt;

	@OneToOne(mappedBy = "user")
	private Student student;

	@OneToOne(mappedBy = "user")
	private Teacher teacher;

}
