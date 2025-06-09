package com.tracker.student.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tracker.student.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private String nomorInduk;
	@JsonIgnore
	private String password;
	private String email;
	private String name;
	private int age;
	@JsonIgnore
	private String role;

	public static UserDetailsImpl build(User user) {
		return new UserDetailsImpl(user.getNomorInduk(), user.getPassword(), user.getEmail(), user.getName(),
				user.getAge(), user.getRole());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (StringUtils.hasText(this.role)) {
			String[] split_role = this.role.replaceAll(" ", "").split(",");
			for (String i : split_role) {
				authorities.add(new SimpleGrantedAuthority(i));
			}
		}
		return authorities;
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
