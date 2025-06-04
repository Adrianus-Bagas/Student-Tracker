package com.tracker.student.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDetailResponseDTO implements Serializable {

	private static final long serialVersionUID = 4468255608636396290L;
	
	private String nomorInduk;

	public String getNomorInduk() {
		return nomorInduk;
	}

	public void setNomorInduk(String nomorInduk) {
		this.nomorInduk = nomorInduk;
	}
	
}
