package com.tracker.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "result", indexes = { @Index(name = "idx_secure_id_result", columnList = "secure_id") })
public class Result extends AbstractBaseEntity {

	private static final long serialVersionUID = 5361204728171089784L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_generator")
	@SequenceGenerator(name = "result_generator", sequenceName = "result_id_seq")
	private Long id;

	@Column(name = "mark", nullable = false)
	private float mark;

	@Column(name = "is_passed", nullable = false)
	private boolean isPassed;

	@Column(name = "semester", nullable = false)
	private int semester;

	@Column(name = "type", nullable = false)
	private String type;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "subject_id", nullable = false, referencedColumnName = "id")
	private Subject subject;
}
