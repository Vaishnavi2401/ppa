package com.cdac;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Ramu Parupalli 
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
@EnableSwagger2
@Configuration
@SpringBootApplication
//@EnableFeignClients
//public class UserAdminApplication extends SpringBootServletInitializer {
	public class UserAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAdminApplication.class, args);
	}
	
	@PostConstruct
	public void init(){
	    TimeZone.setDefault(TimeZone.getTimeZone("IST"));   // It will set UTC timezone
	    System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
	}
	
	@Bean
	public Docket feedbackMasterApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.cdac.web.controller")).paths(PathSelectors.any())
				.build().groupName("UserManagement")

				.apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("User Management Service Documentation").
				description("<b>API Description</b>"
						+ "<p>1) User Management Controller :- </p>")
				.version("1.0.0")
				// .license("Apache License Version 2.0")
				// .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.build();
	}

}
/*
 * @Override protected SpringApplicationBuilder
 * configure(SpringApplicationBuilder application) { return
 * application.sources(applicationClass); }
 * 
 * private static Class<UserAdminApplication> applicationClass =
 * UserAdminApplication.class; }
 */
