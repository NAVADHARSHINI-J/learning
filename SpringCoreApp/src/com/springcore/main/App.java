package com.springcore.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springcore.main.config.AppConfig;
import com.springcore.main.util.DBUtil;
import com.springcore.main.util.MyUtil;

public class App {

	public static void main(String[] args) {
		
		ApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
		MyUtil myUtil=context.getBean(MyUtil.class);
	
		String fName=myUtil.getFirstName("Harry Potter");
		String lName=myUtil.getLastName("Harry Potter");
		System.out.println(fName+" "+lName);
		
		DBUtil db=context.getBean(DBUtil.class);
		db.dbConnect();
		((AnnotationConfigApplicationContext) context).close();
	}

}

/*
 *   A: m1 m2 m3 
 *   A a1=new A(); //100x
 *   a1.m1()
 *   a1.m2()
 *   a1 = null; --let spring do this!!! -- it can do it if A is declared as Bean 
 * 	 a1.m3() //NPE 
 * 
 * 	  @Bean -- spring vl register this object in its context(memory)
 *    A getAInstance(){ 
 *      return new A();
 *    }
 *    
 *    CONTEXT
 *    -------
 *    A a=new A()
 * */
