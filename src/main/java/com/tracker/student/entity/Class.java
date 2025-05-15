package com.tracker.student.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "class", indexes = {
		@Index(name = "idx_secure_id_class", columnList = "secure_id")
})
public class Class extends AbstractBaseEntity {

	private static final long serialVersionUID = -2311074953803080669L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_generator")
	@SequenceGenerator(name = "class_generator", sequenceName = "class_id_seq")
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToOne
	@JoinColumn(name = "teacher_id", nullable = false, referencedColumnName = "id")
	private Teacher teacher;
	
	@ManyToMany
	@JoinTable(name = "class_student", joinColumns = {
			@JoinColumn(referencedColumnName = "id", name = "class_id")},
			inverseJoinColumns = { @JoinColumn(referencedColumnName = "id", name = "student_id") }
			)
	private List<Student> students;
}
