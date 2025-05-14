package com.spring.hibernate.main;

import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.spring.hibernate.main.config.AppConfig;
import com.spring.hibernate.main.controller.ProductController;

public class App {

	public static void main(String[] args) {
		ApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
		ProductController productController=AppFactory.getProductController();
		Scanner sc=new Scanner(System.in);
		while(true) {
		System.out.println("press 1. to insert the data");
		System.out.println("press 2. to delete the data");
		System.out.println("press 3. to get all data");
		System.out.println("0.exit");
		int c=sc.nextInt();
		if(c==0) break;
		switch(c) {
		case 1:	
			productController.addProduct(sc);
			break;
		case 2:
			productController.deleteProduct(sc);
			System.out.println("Product is deleted.....");
			break;
		case 3:
			productController.getAllProduct(sc);
			break;
		}
		}
		((AnnotationConfigApplicationContext)context).close();
		sc.close();
		
	}

}
