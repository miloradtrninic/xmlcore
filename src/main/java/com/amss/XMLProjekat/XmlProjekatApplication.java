package com.amss.XMLProjekat;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.amss.XMLProjekat.repository.dsl.QDSLAliasRegistry;
import com.amss.XMLProjekat.repository.dsl.QDSLPageableResolver;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableTransactionManagement
public class XmlProjekatApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(XmlProjekatApplication.class, args);
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Content-Type", "Date", "Total-Count", "Authorization")
                .maxAge(3600);
            }
        };
    }
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
		resolvers.add(new QDSLPageableResolver(QDSLAliasRegistry.instance()));
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper mm = new ModelMapper();
		return mm;
	}
}
