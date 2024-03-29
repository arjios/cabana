package com.arjios.cabanas.dto;

import java.util.HashSet;
import java.util.Set;

import com.arjios.cabanas.entities.Role;
import com.arjios.cabanas.entities.User;

public class RoleDTO {

	private Long id;
	private String authority;
	
	private Set<User> users = new HashSet<>();
	
	public RoleDTO() {
	}

	public RoleDTO(Long id, String authority) {
		this.id = id;
		this.authority = authority;
	}

	public RoleDTO(Role entity) {
		id = entity.getId();
		authority = entity.getAuthority();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Set<User> getUsers() {
		return users;
	}

}
