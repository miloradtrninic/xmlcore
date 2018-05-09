package com.amss;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class XmlProjekatApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmlProjekatApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper mm = new ModelMapper();
		return mm;
	}
}
