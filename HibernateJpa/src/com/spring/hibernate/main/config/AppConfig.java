package com.spring.hibernate.main.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@ComponentScan(basePackages = {"com.spring.hibernate.main.*"})
@EnableTransactionManagement
public class AppConfig {
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds=new DriverManagerDataSource();
		String userDb="root";
 		String dbPass="password";
 		String url="jdbc:mysql://localhost:3306/fsd_march";
 		String driver = "com.mysql.cj.jdbc.Driver";
 		ds.setDriverClassName(driver);
 		ds.setUrl(url);
 		ds.setUsername(userDb);
 		ds.setPassword(dbPass);
 		return ds;
}
	@Bean
	public Properties setProperties() {
		Properties p=new Properties();
		p.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect" );
		p.put("hibernate.hbm2ddl.auto", "update");
		return p;
	}
	
	@Bean
	public JpaTransactionManager activateTransactionManager(EntityManagerFactory factory) {
 		return new JpaTransactionManager(factory);
 	}
	@Bean
 	public LocalContainerEntityManagerFactoryBean configureEntityManagerFactory(){
 		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
 		factory.setDataSource(dataSource());
 		factory.setPackagesToScan("com.spring.hibernate.main.model");
 		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
 		factory.setJpaProperties(setProperties());
 		return factory; 
 	}
	
}