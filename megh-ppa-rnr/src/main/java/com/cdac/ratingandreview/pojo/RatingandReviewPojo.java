package com.cdac.ratingandreview.pojo;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RatingandReviewPojo {

	//@NotBlank(message = "Review Id cannot be null")
   // @Pattern(regexp = "^[0-9]+$", message = "Invalid Format Only Numbers")
	private int reviewId;
	
	//@NotBlank(message = "Learner Id cannot be null")
   // @Pattern(regexp = "^[A-Za-z0-9- ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String learnerId;
	
	//@NotBlank(message = "Item Id cannot be null")
   // @Pattern(regexp = "^[A-Za-z0-9- ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String itemId;
	

	//@Size(min = 1)
	//@NotBlank(message = "Rating cannot be null")
   // @Pattern(regexp = "^[1-5]+$", message = "Invalid Format Only Numbers between 1 to 5 are allowed")
	private int rating;
	
	private int tenantId;

	//@NotBlank(message = "Review status cannot be null")
   // @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid Format Only alphabets are allowed")
	private String reviewStatus;
	
	//@NotBlank(message = "Review text cannot be null")
    /* @Pattern(regexp = "^[A-Za-z0-9-.,! ]+$", message = "IInvalid Format Only Alphabets & Numbers are allowed")*/
	private String reviewText;
	
	//@NotBlank(message = "Review Type cannot be null")
   // @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets are allowed")
	private String reviewType;
	
	   	
}
