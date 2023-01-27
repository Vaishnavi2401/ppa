package com.cdac.ratingandreview.pojo;


import java.util.Date;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class ReviewReplyDetailsPojo {

	private int reviewId;
	
	private String repliedBy;
		
	private String replyText;
		
	private int replyId;
	
	private Date repliedDate;
	
}
