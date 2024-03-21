package com.arjios.cabanas.entities;

import java.util.Objects;

public class PlazaTables {

	private Integer id;
	private Integer number;
	
	public PlazaTables() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlazaTables other = (PlazaTables) obj;
		return Objects.equals(id, other.id);
	}

}
