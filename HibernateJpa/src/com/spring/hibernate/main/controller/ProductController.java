package com.spring.hibernate.main.controller;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Controller;

import com.spring.hibernate.main.AppFactory;
import com.spring.hibernate.main.model.ProductHibernate;
import com.spring.hibernate.main.service.ProductService;
@Controller     //we can also give @component
public class ProductController {

	public void addProduct(Scanner sc) {
		ProductService productService=AppFactory.getProductService();
		ProductHibernate ph=new ProductHibernate();
		sc.nextLine();
		System.out.println("Enter the title");
		ph.setTitle(sc.nextLine());
		System.out.println("Enter the price");
		ph.setPrice(sc.nextDouble());
		productService.addProduct(ph);
	}

	public void deleteProduct(Scanner sc) {
		ProductService productService=AppFactory.getProductService();
		System.out.println("Enter the product id");
		try {
		productService.deleteProduct(sc.nextInt());
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void getAllProduct(Scanner sc) {
		ProductService productService=AppFactory.getProductService();
		List<ProductHibernate>p=productService.getAllProduct();
		p.stream().forEach(System.out::println);
	}

}
