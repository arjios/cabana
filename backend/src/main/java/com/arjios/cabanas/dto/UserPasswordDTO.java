package com.arjios.cabanas.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class UserPasswordDTO extends UserDTO{
	
	@NotBlank(message = "Senha deve ser valida com minimo de 8 caracteres.")
	@Length(min = 8)
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
