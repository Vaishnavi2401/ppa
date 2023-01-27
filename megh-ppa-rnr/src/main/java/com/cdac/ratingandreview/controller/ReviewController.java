package com.cdac.ratingandreview.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.ratingandreview.model.ReviewDetail;
import com.cdac.ratingandreview.pojo.RatingandReviewPojo;
import com.cdac.ratingandreview.pojo.ReviewAvgPojo;
import com.cdac.ratingandreview.pojo.ReviewDetailsPojo;
import com.cdac.ratingandreview.service.ReviewService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "review")
@CrossOrigin("*")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	

	@PostMapping(value = "saveReview", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(notes = "to save Review", value = "To save Review", response = RatingandReviewPojo.class)
	public String saveReview(@RequestBody RatingandReviewPojo ratingandReviewPojo) {
		// save review to database
		return reviewService.saveReview(ratingandReviewPojo);
	}

	@PostMapping("/updateReview")
	@ApiOperation(notes = "to update Review", value = "To update Review", response = RatingandReviewPojo.class)
	public String updateReview(@RequestBody RatingandReviewPojo ratingandReviewPojo) {
		// save review to database
		return reviewService.updateReview(ratingandReviewPojo);
	}

	@PostMapping("/deleteReview")
	@ApiOperation(notes = "to delete Review", value = "To delete Review")
	public String deleteReview(@NotNull(message = "Review Id Cannot be Null") @RequestParam int reviewId) {
		// save review to database
		return reviewService.deleteReview(reviewId);
	}

	@GetMapping("/byReviewId/{id}")
	@ApiOperation(notes = "to get review details only", value = "To get Review details only", response = ReviewDetail.class)
	public ReviewDetail getreviewByReviewId(@PathVariable int id) {
		ReviewDetail rd = new ReviewDetail();

		rd = reviewService.findbyReviewId(id);
		if (rd != null) {
			return rd;
		} else {
			throw new RuntimeException("Reviews not found for the Id:" + id);
		}
	}

	@GetMapping("/byreviewid")
	@ApiOperation(notes = "to get review details along with learner details", value = "To get Review details including learner details", response = ReviewDetailsPojo.class)
	public ReviewDetailsPojo getreviewDetails(@RequestParam int reviewid) {

		ReviewDetailsPojo rdp = reviewService.getreviewDetails(reviewid);
		if (rdp != null) {
			return rdp;

		} else {
			throw new RuntimeException("Reviews not found for the Id:" + reviewid);
		}
	}
	
	@GetMapping("/byReviewItemid")
	@ApiOperation(notes = "to get review details by Review Item Id", value = "to get review details by Review Item Id", response = ReviewDetailsPojo.class)
	public List<ReviewDetailsPojo> getreviewDetailsbyItemId(@RequestParam String reviewItemId) {

	List<ReviewDetailsPojo> rdplist=reviewService.getreviewDetailsbyItemId(reviewItemId);
		if (rdplist.size()!=0) {
			return rdplist;

		} else {
			throw new RuntimeException("Reviews not found for the review Item Id:" + reviewItemId);
		}
	}
	@PostMapping("/getAvgbyReviewItemid")
	@ApiOperation(notes = "to get average review score by Review Item Id", value = "to get average review score details by Review Item Id", response = ReviewDetailsPojo.class)
	public List<ReviewAvgPojo> getreviewAvgbyItemId(@RequestBody List<ReviewAvgPojo> ItemIdList) {
	List<ReviewAvgPojo> avgrdlist=reviewService.getAvgreviewbyItemId(ItemIdList);
		if (avgrdlist.size()!=0) {
			return avgrdlist;
		} else {
			throw new RuntimeException("Avg Reviews not found for the selected ItemId");
		}
	}
}
