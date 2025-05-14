package com.tracker.student.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student implements Serializable{

	private static final long serialVersionUID = -1255636108451568820L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "secure_id", nullable = false)
	private String secureId;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "age", nullable = false)
	private int age;
}
