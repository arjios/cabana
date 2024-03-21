package com.arjios.cabanas.dto;

import java.io.Serializable;
import java.time.Instant;

import com.arjios.cabanas.entities.Category;
import com.arjios.cabanas.entities.Product;

public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long number;
	private String name;
	private String description;
	private String shortDescription;
	private Double price;
	private String imgUrl;
	private Boolean active;
	private Instant dateInitial;
	private Instant dateFinal;
	private Instant date;
	
	private CategoryDTO category;
	
	public ProductDTO() {

	}

	public ProductDTO(Long id, Long number, String name, String description, String shortDescription, 
			Double price, String imgUrl, Boolean active, Instant dateInitial, Instant dateFinal, Instant date) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.description = description;
		this.shortDescription = shortDescription;
		this.price = price;
		this.imgUrl = imgUrl;
		this.active = active;
		this.dateInitial = dateInitial;
		this.dateFinal = dateFinal;
		this.date = date;
	}
	
	public ProductDTO(Product entity) {
		id = entity.getId();
		number = entity.getNumber();
		name = entity.getName();
		description = entity.getDescription();
		shortDescription = entity.getShortDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		active = entity.getActive();
		dateInitial = entity.getDateInitial();
		dateFinal = entity.getDateFinal();
		date = entity.getDate();
		category = new CategoryDTO(entity.getCategory());
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
	
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Instant getDateInitial() {
		return dateInitial;
	}

	public void setDateInitial(Instant dateInitial) {
		this.dateInitial = dateInitial;
	}

	public Instant getDateFinal() {
		return dateFinal;
	}

	public void setDateFinal(Instant dateFinal) {
		this.dateFinal = dateFinal;
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
