package com.hexa.assetmanagement.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LiquidAssetRequest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(nullable=false)
  private LocalDate requestDate;
  @Column(nullable=false)
  private double requestedAmount;
  @Column(nullable=false,length = 2000)
  private String reason;
  private String status="pending";
  @ManyToOne
  private Employee employee;
  @ManyToOne
  private LiquidAsset liquidAsset;
  
public LiquidAssetRequest(int i, LocalDate localDate, String string, Employee employee2, int j) {
	super();
}
public LiquidAssetRequest(int id, LocalDate requestDate, double requestedAmount, String reason, String status,
		Employee employee, LiquidAsset liquidAsset) {
	super();
	this.id = id;
	this.requestDate = requestDate;
	this.requestedAmount = requestedAmount;
	this.reason = reason;
	this.status = status;
	this.employee = employee;
	this.liquidAsset = liquidAsset;
}
public LiquidAssetRequest(int id, LocalDate requestDate, double requestedAmount, String reason, String status,
		Employee employee) {
	super();
	this.id = id;
	this.requestDate = requestDate;
	this.requestedAmount = requestedAmount;
	this.reason = reason;
	this.status = status;
	this.employee = employee;
}
public Employee getEmployee() {
	return employee;
}
public void setEmployee(Employee employee) {
	this.employee = employee;
}
public LiquidAsset getLiquidAsset() {
	return liquidAsset;
}
public void setLiquidAsset(LiquidAsset liquidAsset) {
	this.liquidAsset = liquidAsset;
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
public double getRequestedAmount() {
	return requestedAmount;
}
public void setRequestedAmount(double requestedAmount) {
	this.requestedAmount = requestedAmount;
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
  
public LiquidAssetRequest() {}   
    
}
