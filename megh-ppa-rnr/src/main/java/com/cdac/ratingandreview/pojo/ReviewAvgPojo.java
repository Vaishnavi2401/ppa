package com.cdac.ratingandreview.pojo;

import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class ReviewAvgPojo {

	
	private String ItemId;

	private double avgScore;
	
	private int tenantId;

}
