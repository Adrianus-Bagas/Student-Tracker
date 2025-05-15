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
@Table(name = "attendance", indexes = {
		@Index(name = "idx_secure_id_attendance", columnList = "secure_id")
})
public class Attendance extends AbstractBaseEntity {

	private static final long serialVersionUID = 2853036171501034208L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendance_generator")
	@SequenceGenerator(name = "attendance_generator", sequenceName = "attendance_id_seq")
	private Long id;
	
	@Column(name = "in_at", nullable = false)
	private String inAt;
	
	@Column(name = "out_at", nullable = false)
	private String outAt;
	
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id")
	private Student student;

}
