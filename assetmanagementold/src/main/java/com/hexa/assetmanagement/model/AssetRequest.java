package com.hexa.assetmanagement.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne; 


@Entity
public class AssetRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private LocalDate requestDate;
	@Column(nullable = false)
	private String reason;
	private String status = "PENDING";
	@ManyToOne
	private Employee employee;
	@ManyToOne
	private Asset asset;
	
	

	public AssetRequest() {
		super(); 
	}

	
	public AssetRequest(int id, LocalDate requestDate, String reason, String status, Employee employee, Asset asset) {
		super();
		this.id = id;
		this.requestDate = requestDate;
		this.reason = reason;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
