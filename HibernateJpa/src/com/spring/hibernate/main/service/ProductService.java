package com.spring.hibernate.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.hibernate.main.model.ProductHibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service      //we can also give @Component
public class ProductService {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void addProduct(ProductHibernate ph) {
		int id=(int) (Math.random()*10000);
		ph.setId(id);
		entityManager.persist(ph);
	}
	@Transactional
	public void deleteProduct(int id) {
		ProductHibernate ph=entityManager.find(ProductHibernate.class,id);
		if(ph==null) {
			throw new IllegalArgumentException("Invalid Id for product......");
		}
		entityManager.remove(ph);		
	}
	//No need give transactinal annotation for select operation
	public List<ProductHibernate> getAllProduct() {
		return entityManager
				.createQuery("Select p from ProductHibernate p",ProductHibernate.class)
				.getResultList();
	}

}
