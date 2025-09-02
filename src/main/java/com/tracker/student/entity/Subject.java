package com.tracker.student.entity;

import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "subject", indexes = { @Index(name = "idx_secure_id_subject", columnList = "secure_id") })
@SQLDelete(sql = "UPDATE subject set is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted=false")
public class Subject extends AbstractBaseEntity {

	private static final long serialVersionUID = 4848156399488993570L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_generator")
	@SequenceGenerator(name = "subject_generator", sequenceName = "subject_id_seq")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "minimum", nullable = false)
	private int minimum;

	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false, referencedColumnName = "id")
	private Teacher teacher;

	@OneToMany(mappedBy = "subject")
	private List<Result> results;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Subject subject = (Subject) obj;
		return id == subject.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
