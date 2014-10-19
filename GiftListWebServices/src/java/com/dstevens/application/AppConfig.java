package com.dstevens.application;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration 
@EnableWebMvc   
@ComponentScan({"com.dstevens"}) 
public class AppConfig {  
	static {
		System.out.println("Hello world " + AppConfig.class.toString());
	}
} 
