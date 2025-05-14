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
public class AssetAllocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private LocalDate allocationDate;
	private LocalDate returnDate;
	private String status = "ALLOCATED";
	@Column(length = 2000)
	private String reason;

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@ManyToOne
	private Asset asset;  //findByAssetId 

	@ManyToOne
	private Employee employee;
	
	public AssetAllocation() {
		super();
	}
	public AssetAllocation(int id, LocalDate allocationDate, LocalDate returnDate, String status) {
		super();
		this.id = id;
		this.allocationDate = allocationDate;
		this.returnDate = returnDate;
		this.status = status;
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

	public LocalDate getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(LocalDate allocationDate) {
		this.allocationDate = allocationDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	@Override
	public int hashCode() {
		return Objects.hash(allocationDate, asset, employee, id, returnDate, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssetAllocation other = (AssetAllocation) obj;
		return Objects.equals(allocationDate, other.allocationDate) && Objects.equals(asset, other.asset)
				&& Objects.equals(employee, other.employee) && id == other.id
				&& Objects.equals(returnDate, other.returnDate) && Objects.equals(status, other.status);
	}

}
