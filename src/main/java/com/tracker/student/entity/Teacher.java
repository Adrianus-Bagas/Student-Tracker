package com.tracker.student.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "teacher", indexes = {
		@Index(name = "idx_secure_id_teacher", columnList = "secure_id")
})
public class Teacher extends AbstractBaseEntity {

	private static final long serialVersionUID = 2432345570938849846L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_generator")
	@SequenceGenerator(name = "teacher_generator", sequenceName = "teacher_id_seq")
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "age", nullable = false)
	private int age;
	
	@OneToMany(mappedBy = "teacher")
	private List<Subject> subjects;
	
	@OneToOne(mappedBy = "teacher")
	private Class teacherClass;
}
