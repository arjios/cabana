package com.arjios.cabanas.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order_items")
public class OrderItems implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userCode;
	private Instant dateTime;
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	public OrderItems() {
	}

	public OrderItems(Long id, Instant dateTime, Long userCode) {
		this.id = id;
		this.dateTime = dateTime;
		this.userCode = userCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDateTime() {
		return dateTime;
	}

	public void setDateTime(Instant dateTime) {
		this.dateTime = dateTime;
	}

	public Long getUserCode() {
		return userCode;
	}

	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}

	public Order getOrder() {
		return order;
	}

	public Product getProduct() {
		return product;
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
		OrderItems other = (OrderItems) obj;
		return Objects.equals(id, other.id);
	}

}
