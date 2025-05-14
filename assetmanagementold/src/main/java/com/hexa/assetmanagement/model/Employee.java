package com.hexa.assetmanagement.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String contact;

	@Column(length = 600)
	private String address;

	@ManyToOne
	private Department department;

	@OneToOne
	private User user;
	public Employee() {
		super();
	}

	public Employee(int id, String name, String email, String contact, String address, Department department,
			User user) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.contact = contact;
		this.address = address;
		this.department = department;
		this.user = user;
	}

	public Employee(int id, String name, String email, String contact, String address, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.contact = contact;
		this.address = address;
		this.department = department;
	}

	
	
	public Employee(int i, String string, String string2, String string3, String string4, String string5) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, contact, department, email, id, name, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(address, other.address) && Objects.equals(contact, other.contact)
				&& Objects.equals(department, other.department) && Objects.equals(email, other.email) && id == other.id
				&& Objects.equals(name, other.name) && Objects.equals(user, other.user);
	}

}