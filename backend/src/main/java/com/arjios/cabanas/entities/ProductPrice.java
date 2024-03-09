package com.arjios.cabanas.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_product_price")
public class ProductPrice implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	//private Long id_product; chave composta

	private Instant date_initial;
	private Instant date_final;
	private Double price;
	private Double discount;

	public ProductPrice() {
	}

	public ProductPrice(Long id, Instant date_initial, Instant date_final, 
			Double price, Double discount) {
		this.id = id;
		this.date_initial = date_initial;
		this.date_final = date_final;
		this.price = price;
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDate_initial() {
		return date_initial;
	}

	public void setDate_initial(Instant date_initial) {
		this.date_initial = date_initial;
	}

	public Instant getDate_final() {
		return date_final;
	}

	public void setDate_final(Instant date_final) {
		this.date_final = date_final;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
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
		ProductPrice other = (ProductPrice) obj;
		return Objects.equals(id, other.id);
	}

}
