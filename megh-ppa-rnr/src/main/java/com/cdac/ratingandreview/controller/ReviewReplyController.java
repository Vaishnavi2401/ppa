package com.cdac.ratingandreview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.ratingandreview.pojo.ReviewReplyPojo;
import com.cdac.ratingandreview.service.ReviewService;

import io.swagger.annotations.ApiOperation;


@Component
@RestController
@RequestMapping(value="review", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class ReviewReplyController {

	@Autowired
	private ReviewService reviewService;
	
	
	@PostMapping("/savereply")
	@ApiOperation(notes = "to save Review Reply",value = "To save Review Reply",response = ReviewReplyPojo.class)
	public String saveReviewReply(@RequestBody ReviewReplyPojo replyPojo) {
		// save review to database
		return reviewService.saveReviewReply(replyPojo); 
	}
	
	@PostMapping("/updatereply")
	@ApiOperation(notes = "to update Review Reply",value = "To update Review Reply",response = ReviewReplyPojo.class)
	public String updateReviewReply(@RequestBody ReviewReplyPojo replyPojo) {
		// update review to database
		return reviewService.updateReviewReply(replyPojo); 
	}
	
	@PostMapping("/deletereply")
	@ApiOperation(notes = "to Delete Review Reply",value = "To Delete Review Reply",response = ReviewReplyPojo.class)
	public String deleteReviewReply(@RequestParam int replyId) {
		// Delete review from database
		return reviewService.deleteReviewReply(replyId); 
	}
}
