package com.cdac.ratingandreview;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RatingandReviewApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RatingandReviewApplication.class, args);
	}
	@PostConstruct
	public void init(){
	    TimeZone.setDefault(TimeZone.getTimeZone("IST"));   // It will set UTC timezone
	    System.out.println("R and R application running in UTC timezone :"+new Date());   // It will print UTC timezone
	}

}
