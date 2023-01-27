package com.cdac.announcementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class AnnouncementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnouncementServiceApplication.class, args);
	}
	@Bean
	public Docket feedbackMasterApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.cdac.announcementservice.controllers"))
				.paths(PathSelectors.any()).build().groupName("Announcement")

				.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("NeGL Announcement Service Documentation")
				.description("<b>API Description</b>" + "<p>1) Announcement Controller :- </p>").version("1.0.0")
				// .license("Apache License Version 2.0")
				// .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.build();
	}

}
