package com.springcore.main.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.springcore.main.*"})
//spring, go and scan the whole project 
public class MyConfig {
	/* create a bean of all those classes where i have marked @Component annotation */
}
