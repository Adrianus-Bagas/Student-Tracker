package com.tracker.student.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

	private static final long serialVersionUID = 2432345570938849846L;
	
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
