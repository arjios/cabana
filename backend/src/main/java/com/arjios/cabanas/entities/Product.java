package com.arjios.cabanas.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.arjios.cabanas.entities.enuns.ProductOrigin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private Long number;
	@Column(unique = true)
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	private String shortDescription;
	private ProductOrigin origin;
	private Double price;
	private String imgUrl;
	private Boolean active;
	private Instant initialDate;
	private Instant finalDate;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant date;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	public Product() {
	}

	public Product(Long id, Long number, String name, String description, String shortDescription, ProductOrigin origin, 
			Double price, String imgUrl, Boolean active, Instant initialDate, Instant finalDate, Instant date) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.description = description;
		this.shortDescription = shortDescription;
		this.origin = origin;
		this.price = price;
		this.imgUrl = imgUrl;
		this.active = active;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.date = date;
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

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
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

	public ProductOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(ProductOrigin origin) {
		this.origin = origin;
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
		return initialDate;
	}

	public void setDateInitial(Instant initialDate) {
		this.initialDate = initialDate;
	}

	public Instant getDateFinal() {
		return finalDate;
	}

	public void setDateFinal(Instant finalDate) {
		this.finalDate = finalDate;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	} 

}
