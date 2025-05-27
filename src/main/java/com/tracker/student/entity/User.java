package com.tracker.student.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "app_user", indexes = {
		@Index(name = "idx_nomor_induk_user", columnList = "nomor_induk")
})
public class User extends AbstractBaseEntity implements UserDetails {

	private static final long serialVersionUID = -504151175727503690L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq")
	private Long id;
	
	@Column(name = "nomor_induk", nullable = false)
	private String nomorInduk;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "age", nullable = false)
	private int age;
	
	//----------Teacher Relation----------
	@OneToMany(mappedBy = "teacher")
	private List<Subject> subjects;
	
	@OneToOne(mappedBy = "teacher")
	private Class teacherClass;
	//----------Teacher Relation----------
		
	//----------Student Relation----------
	@ManyToOne()
	@JoinColumn(name = "class_id", nullable = false, referencedColumnName = "id")
	private Class studentClass;
	@OneToMany(mappedBy = "student")
	private List<Result> results;
	//----------Student Relation----------
	
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name="user_id", referencedColumnName = "id")
	}, inverseJoinColumns = {
			@JoinColumn(name="role_id", referencedColumnName = "id")
	})
	private List<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.nomorInduk;
	}

}
