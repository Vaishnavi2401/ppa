package com.cdac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableWebMvc
public class FeedbackApplication {
	/*
	 * @Bean public MethodValidationPostProcessor methodValidationPostProcessor() {
	 * return new MethodValidationPostProcessor(); }
	 */

	public static void main(String[] args) {
		
	    Logger logger = LoggerFactory.getLogger(FeedbackApplication.class);

		 logger.trace("A TRACE Message");
	        logger.debug("A DEBUG Message");
	        logger.info("An INFO Message");
	        logger.warn("A WARN Message");
	        logger.error("An ERROR Message");
		
		SpringApplication.run(FeedbackApplication.class, args);
	}

}

