package com.hexa.assetmanagement.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Asset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String model;
	@Column(nullable = false)
	private String status = "Available";
	@Column(nullable = false)
	private LocalDate date;
	@Column(nullable = false,length = 1000)
	private String configuration;
	@Column(length = 2000)
	private String description;
	@Column(nullable = false)
	private int quantity;
	
	@ManyToOne
	private Category category;
	
	public Asset() {
		super();
	}

	public Asset(int id, String name, String model, String status, LocalDate date, String configuration,
			String description, int quantity, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.model = model;
		this.status = status;
		this.date = date;
		this.configuration = configuration;
		this.description = description;
		this.quantity = quantity;
		this.category = category;
	}

	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, configuration, date, description, id, model, name, quantity, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asset other = (Asset) obj;
		return Objects.equals(category, other.category) && Objects.equals(configuration, other.configuration)
				&& Objects.equals(date, other.date) && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(model, other.model) && Objects.equals(name, other.name) && quantity == other.quantity
				&& Objects.equals(status, other.status);
	}
}
