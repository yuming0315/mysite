package com.douzone.mysite.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileuploadConfig implements WebMvcConfigurer{
	//multipartresolver
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartresolver = new CommonsMultipartResolver();
		multipartresolver.setDefaultEncoding("utf-8");
		multipartresolver.setMaxUploadSize(52428800);
		multipartresolver.setMaxInMemorySize(52428800);
		
		return multipartresolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/upload-images/**")
		.addResourceLocations("file:/mysite-uploads/");
	}
	
	
}
