package com.cdac.feedback;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
//@EnableEurekaClient

public class NeGlFeedbackApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NeGlFeedbackApplication.class, args);
	}

	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	            return application.sources(NeGlFeedbackApplication.class); 
	    }

	@Bean
	public Docket feedbackMasterApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.cdac.feedback.controller")).paths(PathSelectors.any())
				.build().groupName("Feedback")

				.apiInfo(metaData());
	}

	

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("NeGL Feedback Documentation").
				description("Feedback Module \n \n <b>Http Codes Status:</b> \n \n"
						+ "  200 : SUCCESSFUL \n" 
						+ " 201 : CREATED \n"
						+ " 204  : NO CONTENT - Details NOT found with provided Id \n"
						+ "400: Bad Request \n"
						+ " 409 : Duplicate Data \\ Conflict \\ Already Mapped with Foreign Table  \n"
						+ "500 : Failed "
						+"\n \n "
						+" <b>Abbrevation(s) used: </b> "
						+ "TF- True or False. "
						+ "TA - Text Area / Descritptive. "
						+ "SC - Single Choice. "
						+ "MC- Multiple Choice."
						)
				.version("1.0.0")
				// .license("Apache License Version 2.0")
				// .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.build();
	}
	
	
	/* Code for reading messages from property file messages.properties 
	 * Used properties for validation messages*/
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	    
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}

	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}

}
