package com.cdac.ratingandreview.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class ReviewDetailsPojo {

	private String learnerUsername;

	private String firstName;

	private String lastName;

	private int rating;

	private String reviewText;
	
	private int tenantId;

	private String reviewType;
	
	private String sentimentAnalysis;
	
	private Date creationTime;
	
	private int reviewId;
	
	private String profilePicUrl;
	
	private List<ReviewReplyDetailsPojo> reviewReply;

}
