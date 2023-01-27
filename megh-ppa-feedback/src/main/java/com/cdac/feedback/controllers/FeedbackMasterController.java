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

import com.cdac.feedback.dto.CustomResponeMasterDto;
import com.cdac.feedback.dto.FeedbackMasterDto;
import com.cdac.feedback.models.FeedbackMaster;
import com.cdac.feedback.models.QuestionMaster;
import com.cdac.feedback.models.ResponseMaster;
import com.cdac.feedback.services.FeedbackServices;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("*")
@RestController
@RequestMapping(value="feedback")
public class FeedbackMasterController {

	
	private static final Logger logger = LoggerFactory.getLogger(FeedbackMasterController.class);

	private static final String NEW_FEEDBACK_LOG = "New Feedback was created :{}";
	private static final String UPDATE_FEEDBACK_LOG = "Feedback  was updated :{}";
	private static final String ERROR = "Error Occured in FeedbackController:{}";

	@Autowired
	FeedbackServices service;
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Saves Feedback details ")
	public ResponseEntity<FeedbackMaster> addFeedback(@Valid @RequestBody FeedbackMasterDto feedbackMasterDto) {

		try {
			
			FeedbackMaster feedbackMaster = service.addFeeback(feedbackMasterDto);

			logger.info(NEW_FEEDBACK_LOG, feedbackMaster.getFeedbackId());

			return ResponseEntity.status(HttpStatus.CREATED).body(feedbackMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Feedback details ")
	public ResponseEntity<FeedbackMaster> updateFeedback(@Valid @RequestBody FeedbackMasterDto feedbackMasterDto) {

		try {
			

			Optional<FeedbackMaster> feeback = service.getFeedBackById(feedbackMasterDto.getFeedbackId());

			if (!feeback.isPresent()) {

				return ResponseEntity.noContent().build();
			}

			FeedbackMaster feedbackMaster = service.updateFeeback(feedbackMasterDto);

			logger.info(UPDATE_FEEDBACK_LOG, feedbackMaster.getFeedbackId());

			return ResponseEntity.ok().body(feedbackMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@DeleteMapping(value = "{feedbackId}")
	@ApiOperation(value = "Deletes Feedback on providing feedbackId", notes = "")
	public ResponseEntity<QuestionMaster> deleteFeedbackById(
			@Min(value=1,message = "{feedbackid.not.empty}") @PathVariable(value = "feedbackId") @ApiParam(value = "Only Integer Numbers") int feedbackId) {

		try {
			
			Optional<FeedbackMaster> feeback = service.getFeedBackById(feedbackId);
			
			if (!feeback.isPresent()) {

				return ResponseEntity.noContent().build();
			}
			
			
			//checking if the feedbackId is mapped in responseMaster if present then
			//we cannot delete it 
			
			
			List<CustomResponeMasterDto> response = service.findByFeedbackId(feedbackId);

			if (!response.isEmpty()) {

				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			
			
			service.deleteFeedback(feedbackId);
			
			return ResponseEntity.ok().build();

		} 
		
		
		catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	
	}
	
		
	@GetMapping(value= "{feedbackId}")
	@ApiOperation(value = "Gets Feedback detail by feedbackId", notes = "")
	public ResponseEntity<FeedbackMaster> getFeedbackDetailsById(@Min(value=1,message =  "{feedbackid.not.empty}") @PathVariable(value = "feedbackId") @ApiParam(value = "Only Integer Numbers") int feedbackId) {

		try {
			

			Optional<FeedbackMaster> feeback = service.getFeedBackById(feedbackId);
			
			if (!feeback.isPresent()) {

				return ResponseEntity.noContent().build();
			}
		
			return ResponseEntity.ok().body(feeback.get());

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	
	}
	
	
	
	@GetMapping
	@ApiOperation(value = "Gets ALL Feedback detail", notes = "")
	public ResponseEntity<List<FeedbackMaster>> getAllFeedbackDetails() {

		try {
			

			List<FeedbackMaster> feedbackMaster = service.getAllFeedbackDetails();
		
			return ResponseEntity.ok().body(feedbackMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	
	}
	
}
