package com.cdac.meghst.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	@Value("${extern.resoures.path}")
	private String path;
	@Value("${extern.resources.Dir}")
	private String resourceDir;
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/"+resourceDir+"/**").addResourceLocations("file:"+path);
	}
}


