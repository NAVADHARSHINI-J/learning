package com.spring.hibernate.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductHibernate {
	@Id
 	private int id; 
 	
 	private String title;
 
    private double price; 
 	
 	@Override
	public String toString() {
		return "ProductHibernate [id=" + id + ", title=" + title + ", price=" + price + "]";
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
 		return id;
 	}
 
 	public void setId(int id) {
 		this.id = id;
 	}
 
 	public String getTitle() {
 		return title;
 	}
 
 	public void setTitle(String title) {
 		this.title = title;
 	}
}
