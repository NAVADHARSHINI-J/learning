package com.springcore.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springcore.main.util.DBUtil;
import com.springcore.main.util.MyUtil;

@Configuration
public class AppConfig {
	@Bean
	public MyUtil getMyUtilInstance() {
		return new MyUtil();
	}
	@Bean
	public DBUtil getDBUtilInstance() {
		return new DBUtil();
	}
}


