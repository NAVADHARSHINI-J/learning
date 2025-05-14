package com.hexa.assetmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LiquidAsset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private double totalAmount;
	@Column(nullable = false)
	private double remainingAmount;
	@Column(length = 2000)
	private String description;
	private String status;

	
	public LiquidAsset(String name, double totalAmount, double remainingAmount, String description, String status) {
		super();
		this.name = name;
		this.totalAmount = totalAmount;
		this.remainingAmount = remainingAmount;
		this.description = description;
		this.status = status;
	}

	public LiquidAsset(int id, String name, double totalAmount, double remainingAmount, String description,
			String status) {
		super();
		this.id = id;
		this.name = name;
		this.totalAmount = totalAmount;
		this.remainingAmount = remainingAmount;
		this.description = description;
		this.status = status;
	}

	public LiquidAsset() {
		super();
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

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
