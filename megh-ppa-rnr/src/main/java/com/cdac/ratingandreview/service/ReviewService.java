package com.cdac.ratingandreview.service;



import java.util.List;

import com.cdac.ratingandreview.model.ReviewDetail;
import com.cdac.ratingandreview.pojo.RatingandReviewPojo;
import com.cdac.ratingandreview.pojo.ReviewAvgPojo;
import com.cdac.ratingandreview.pojo.ReviewDetailsPojo;
import com.cdac.ratingandreview.pojo.ReviewReplyPojo;



public interface ReviewService {
	
	
	String saveReview(RatingandReviewPojo ratingandreviewPojo);
	
	String updateReview(RatingandReviewPojo ratingandreviewPojo);
	
	String deleteReview(int reviewid);
	
	String saveReviewReply(ReviewReplyPojo replyPojo);
	String updateReviewReply(ReviewReplyPojo replyPojo);
	String deleteReviewReply(int replyId);
	ReviewDetail findbyReviewId(int reviewid);

	ReviewDetailsPojo getreviewDetails(int reviewid);
	List<ReviewDetailsPojo> getreviewDetailsbyItemId(String reviewItemId);
	List<ReviewAvgPojo> getAvgreviewbyItemId(List<ReviewAvgPojo> CourseList);
}
