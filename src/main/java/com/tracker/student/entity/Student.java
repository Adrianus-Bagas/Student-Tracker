package com.tracker.student.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@Table(name = "student", indexes = {
		@Index(name = "idx_secure_id_student", columnList = "secure_id")
})
public class Student extends AbstractBaseEntity {

	private static final long serialVersionUID = -1255636108451568820L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
	@SequenceGenerator(name = "student_generator", sequenceName = "student_id_seq")
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "age", nullable = false)
	private int age;
	
	@OneToMany(mappedBy = "student")
	private List<Attendance> attendances;
	
	@OneToMany(mappedBy = "student")
	private List<Result> results;
}
