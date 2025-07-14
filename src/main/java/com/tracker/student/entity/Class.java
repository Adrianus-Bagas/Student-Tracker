package com.tracker.student.entity;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "class")
@SQLDelete(sql = "UPDATE class set is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted=false")
public class Class extends AbstractBaseEntity {

	private static final long serialVersionUID = -2311074953803080669L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_generator")
	@SequenceGenerator(name = "class_generator", sequenceName = "class_id_seq")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "studentClass")
	private List<Student> students;
}
