package com.cdac.ratingandreview.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewReplyPojo {

	//@NotBlank(message = "ReviewId cannot be null")
    //@Pattern(regexp = "^[0-9]+$", message = "Invalid Format Only Numbers")
	private int reviewId;
	
	//@NotBlank(message = "user/Learner ID cannot be null")
   // @Pattern(regexp = "^[A-Za-z0-9- ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String repliedBy;
	
	
	//@NotBlank(message = "Review Reply text cannot be null")
    /* @Pattern(regexp = "^[A-Za-z0-9-.,! ]+$", message = "IInvalid Format Only Alphabets & Numbers are allowed")*/
	private String replyText;
	
//	@NotBlank(message = "Reply Id cannot be null")
  //  @Pattern(regexp = "^[0-9]+$", message = "Invalid Format Only Numbers")
	private int replyId;
}
