package com.arjios.cabanas.entities;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order")
public class Order {
	
	@Id
	private Long id;
	private Integer room;
	private Instant timeDate;

	@ManyToOne
	private List<Product> products;

	public Order() {
	}

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Instant getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(Instant timeDate) {
		this.timeDate = timeDate;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(Product product) {
		products.add(product);
	}

}
