package com.arjios.cabanas.dto;

public class UserPasswordDTO extends UserDTO{
	
	private String password;

	public UserPasswordDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
