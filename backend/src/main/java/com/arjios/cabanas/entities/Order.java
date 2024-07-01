package com.arjios.cabanas.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.arjios.cabanas.entities.enuns.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Instant openTime;
	private Instant closeTime;
	private OrderStatus orderStatus;
	private Double total;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_order_orderItems",
				joinColumns = @JoinColumn(name = "order_id"),
				inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<OrderItems> items;
	
	public Order() {
	}

	public Order(Long id, Instant openTime, Instant closeTime, OrderStatus orderStatus,
			Double total) {
		this.id = id;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.orderStatus = orderStatus;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Instant openTime) {
		this.openTime = openTime;
	}

	public Instant getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Instant closeTime) {
		this.closeTime = closeTime;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public List<OrderItems> getItems() {
		return items;
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
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

}
