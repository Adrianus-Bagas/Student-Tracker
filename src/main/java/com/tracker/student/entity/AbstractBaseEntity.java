package com.tracker.student.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {
	
	private static final long serialVersionUID = 2463509211225598151L;

	@Column(name = "secure_id", nullable = false, unique = true)
	private String secureId = UUID.randomUUID().toString();
	
	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	private boolean isDeleted;

}
