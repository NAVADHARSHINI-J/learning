package com.test.testproject.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class DoctorPatient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private LocalDate appointment_date;
	
	@ManyToOne
	private Doctor doctor;   //findByDoctor
	@ManyToOne
	private Patient patient;
	
	public DoctorPatient() {
		super();
		}
	
	public DoctorPatient(int id, LocalDate appointment_date, Doctor doctor, Patient patient) {
		super();
		this.id = id;
		this.appointment_date = appointment_date;
		this.doctor = doctor;
		this.patient = patient;
	}

	public LocalDate getAppointment_date() {
		return appointment_date;
	}
	public void setAppointment_date(LocalDate appointment_date) {
		this.appointment_date = appointment_date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
}
