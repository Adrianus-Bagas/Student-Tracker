package com.tracker.student.entity;

import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
@SQLDelete(sql = "UPDATE student set is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted=false")
public class Student extends AbstractBaseEntity {

	private static final long serialVersionUID = 4278852940272766792L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
	@SequenceGenerator(name = "student_generator", sequenceName = "student_id_seq")
	private Long id;

	@Column(name = "is_promoted", nullable = false, columnDefinition = "boolean default false")
	private boolean isPromoted;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "class_id", nullable = false)
	private Class studentClass;

	@OneToMany(mappedBy = "student")
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
		Student student = (Student) obj;
		return id == student.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
