package com.arjios.cabanas.dto;

import java.time.Instant;

import com.arjios.cabanas.entities.Order;
import com.arjios.cabanas.entities.OrderItems;
import com.arjios.cabanas.entities.Product;

public class OrderItemsDTO {

	private Long id;
	private Long userCode;
	private Instant dateTime;
	private Product product;
	private Order order;
	
	public OrderItemsDTO() {
	}

	public OrderItemsDTO(Long id, Long userCode, Instant dateTime, Product product, Order order) {
		this.id = id;
		this.userCode = userCode;
		this.dateTime = dateTime;
		this.product = product;
	}

	public OrderItemsDTO(OrderItems entity) {
		id = entity.getId();
		userCode = entity.getUserCode();
		dateTime = entity.getDateTime();
		product = entity.getProduct();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserCode() {
		return userCode;
	}

	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}

	public Instant getDateTime() {
		return dateTime;
	}

	public void setDateTime(Instant dateTime) {
		this.dateTime = dateTime;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
