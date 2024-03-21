package com.arjios.cabanas.entities;

import java.util.List;
import java.util.Objects;

public class SalesPlaza {

	private Integer id;
	private Integer number;
	private String name;
	
	private List<PlazaTables> tables;
	
	public SalesPlaza() {

	}

	public SalesPlaza(Integer id, Integer number, String name) {
		this.id = id;
		this.number = number;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PlazaTables> getTables() {
		return tables;
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
		SalesPlaza other = (SalesPlaza) obj;
		return Objects.equals(id, other.id);
	}

}
