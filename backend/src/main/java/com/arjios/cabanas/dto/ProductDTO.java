package com.arjios.cabanas.dto;

import java.io.Serializable;
import java.time.Instant;

import com.arjios.cabanas.entities.Category;
import com.arjios.cabanas.entities.Product;

public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private String shortDescription;
	private Double price;
	private String imgUrl;
	private Instant date;
	
	private CategoryDTO category;
	
	public ProductDTO() {

	}

	public ProductDTO(Long id, String name, String description, String shortDescription, 
			Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.shortDescription = shortDescription;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}
	
	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		shortDescription = entity.getShortDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		date = entity.getDate();
	}

	public ProductDTO(Product entity, Category category) {
		this(entity);
		this.category = new CategoryDTO(category); 
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

}
