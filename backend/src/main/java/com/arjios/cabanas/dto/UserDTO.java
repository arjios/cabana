package com.arjios.cabanas.dto;

import com.arjios.cabanas.entities.Role;
import com.arjios.cabanas.entities.User;

public class UserDTO {

	private Long id;
	private String name;
	private String lastName;
	private String email;
	
	private Role role;
	
	public UserDTO() {
	}

	public UserDTO(Long id, String name, String lastName, String email) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		name = entity.getLastName();
		lastName = entity.getLastName();
		email = entity.getEmail();
		role = entity.getRole();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

}
