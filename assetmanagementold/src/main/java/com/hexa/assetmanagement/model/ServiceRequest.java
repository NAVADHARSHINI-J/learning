package com.hexa.assetmanagement.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ServiceRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private LocalDate requestDate; // findByRequestDate
	@Column(nullable = false, length = 2000)
	private String reason;
	private String imageUrl;
	private String status = "PENDING"; // findByStatus
	@ManyToOne
	private Employee employee; // findByEmployee
	@ManyToOne
	private Asset asset;

	public ServiceRequest() {
		super();
	}
	public ServiceRequest(int id, LocalDate requestDate, String reason, String imageUrl, String status,
			Employee employee, Asset asset) {
		super();
		this.id = id;
		this.requestDate = requestDate;
		this.reason = reason;
		this.imageUrl = imageUrl;
		this.status = status;
		this.employee = employee;
		this.asset = asset;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}