package com.cdac.feedback.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.feedback.dto.FeedbackQuestionMapDto;
import com.cdac.feedback.models.FeedbackMaster;
import com.cdac.feedback.models.FeedbackQuestionMap;
import com.cdac.feedback.models.QuestionMaster;
import com.cdac.feedback.services.FeedbackServices;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("*")
@RestController
@RequestMapping(value="feedbackmap")
public class FeedBackQuestionMapController {

	private static final Logger logger = LoggerFactory.getLogger(FeedBackQuestionMapController.class);

	private static final String NEW_FEEDBACKMAP_LOG = "New FeedbackMap was created :{}";
	private static final String UPDATE_FEEDBACKMAP_LOG = "FeedbackMap  was updated :{}";
	private static final String ERROR = "Error Occured in FeedbackMapController:{}";

	@Autowired
	FeedbackServices service;
	

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Saves Feedback_Question Mappings  ")
	public ResponseEntity<FeedbackQuestionMap> addFeedbackMap(@Valid @RequestBody FeedbackQuestionMapDto feedbackQuestionMapDto) {

		try {
			
			service.addFeedbackMap(feedbackQuestionMapDto);

			logger.info(NEW_FEEDBACKMAP_LOG, "FeedbackMapping Done Successfully");

			return ResponseEntity.status(HttpStatus.CREATED).build();

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Feedback_Question Mappings  ")
	public ResponseEntity<FeedbackQuestionMap> updateFeedback(@Valid @RequestBody FeedbackQuestionMapDto feedbackQuestionMapDto) {

	try {
		
		List<FeedbackQuestionMap> feedbackMap = service.getQuestionsByFeedbackId(feedbackQuestionMapDto.getFeedbackId());
		
		if (feedbackMap.isEmpty()) {

			return ResponseEntity.noContent().build();
		}
			
			service.updateFeedbackMap(feedbackQuestionMapDto);

			logger.info(UPDATE_FEEDBACKMAP_LOG, "FeedbackMapping Updated Successfully");

			return ResponseEntity.ok().build();

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	
	
	
	
	@DeleteMapping(value = "{feedbackId}")
	@ApiOperation(value = "Deletes Feedback_Question Mappings on providing feedbackId", notes = "")
	public ResponseEntity<QuestionMaster> deleteFeedbackById(
			@Min(value=1,message = "{feedbackid.not.empty}") @PathVariable(value = "feedbackId") @ApiParam(value = "Accepts Only Integer") int feedbackId) {

		try {
			
			List<FeedbackQuestionMap> feedbackMap = service.getQuestionsByFeedbackId(feedbackId);
			
			if (feedbackMap.isEmpty()) {

				return ResponseEntity.noContent().build();
			}
			
			service.deleteMapByFeedbackId(feedbackId);
			
			return ResponseEntity.ok().build();

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	
	}
	
	
	
		
	@GetMapping(value= "{typeId}/{Id}")
	@ApiOperation(value = "Get Questions by feedbackId", notes = "")
	public ResponseEntity<List<FeedbackQuestionMap>> getQuestionsByFeedbackId(@Min(value=1,message = "{feedbackid.not.empty}") @PathVariable(value = "Id") @ApiParam(value = "Accepts Only Integer") int Id,@Min(value=1,message = "{feedbackid.not.empty}") @PathVariable(value = "typeId") @ApiParam(value = "Accepts Only Integer") int typeId) {

		
		
		int feedbackId = 0;
		
		
		Optional <FeedbackMaster> feedback = service.getFeedBackByTypeAndId(typeId, Id);
		
		if(!feedback.isPresent()) {
			
			return ResponseEntity.noContent().build();
			
		}
		
		
		try {
			

			List<FeedbackQuestionMap> feedbackMap = service.getQuestionsByFeedbackId(feedback.get().getFeedbackId());
			
			if (feedbackMap.isEmpty()) {

				return ResponseEntity.noContent().build();
			}
		
			return ResponseEntity.ok().body(feedbackMap);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	
	}
	
	

}
