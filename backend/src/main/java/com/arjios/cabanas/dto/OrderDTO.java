package com.arjios.cabanas.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.arjios.cabanas.entities.Order;
import com.arjios.cabanas.entities.OrderItems;
import com.arjios.cabanas.entities.enuns.OrderStatus;

public class OrderDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant openTime;
	private Instant closeTime;
	private OrderStatus orderStatus;
	private Double total;
	
	private List<OrderItems> orderItems;

	public OrderDTO() {
	}

	public OrderDTO(Long id,  OrderStatus orderStatus,
			Double total) {
		this.id = id;
		this.openTime = Instant.now();
		this.closeTime = null;
		this.orderStatus = orderStatus;
		this.total = total;
	}
	
	public OrderDTO(Order entity) {
		id = entity.getId();
		openTime = entity.getOpenTime();
		closeTime = entity.getCloseTime();
		orderStatus = entity.getOrderStatus();
		total = entity.getTotal();
		orderItems = entity.getItems();
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

	public List<OrderItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}

}
