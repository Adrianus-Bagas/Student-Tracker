package com.tracker.student.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "teacher")
public class Teacher extends AbstractBaseEntity {

	private static final long serialVersionUID = -5188181620775486650L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_generator")
	@SequenceGenerator(name = "teacher_generator", sequenceName = "teacher_id_seq")
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
	private User user;

	@OneToOne(mappedBy = "teacher")
	private Class teacherClass;

	@OneToMany(mappedBy = "teacher")
	private List<Subject> subjects;
}
