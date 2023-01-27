package com.cdac.courseorg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableFeignClients

public class NgelCourseOrganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NgelCourseOrganizerApplication.class, args);
	}
	
	@Bean
	public Docket feedbackMasterApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.cdac.courseorg.controllers")).paths(PathSelectors.any())
				.build().groupName("CourseOrganizer")

				.apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("NeGL Course Organizer Documentation").
				description("<b>API Description</b>"
						+ "<p>1) Content Detail Controller :- </p>")
				.version("1.0.0")
				// .license("Apache License Version 2.0")
				// .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.build();
	}

}
