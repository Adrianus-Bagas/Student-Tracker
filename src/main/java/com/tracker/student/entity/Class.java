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
import jakarta.persistence.OneToMany;
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
		Class class1 = (Class) obj;
		return id == class1.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
