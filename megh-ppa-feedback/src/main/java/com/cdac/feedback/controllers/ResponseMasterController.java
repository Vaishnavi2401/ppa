package com.cdac.feedback.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.feedback.dto.CustomResponeMasterDto;
import com.cdac.feedback.dto.ResponesMasterDto;
import com.cdac.feedback.dto.ResponseCount;
import com.cdac.feedback.models.FeedbackMaster;
import com.cdac.feedback.models.ResponseMaster;
import com.cdac.feedback.models.TypeMaster;
import com.cdac.feedback.services.FeedbackServices;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "feedbackResponse")
@Validated
public class ResponseMasterController {

	private static final Logger logger = LoggerFactory.getLogger(ResponseMasterController.class);

	private static final String NEW_RESPONSE_LOG = "New Response was created with id:{}";
	private static final String ERROR = "Error Occured in ResponseController:{}";

	@Autowired
	FeedbackServices service;

	@GetMapping(value = "{feedbackId}")
	@ApiOperation(value = "Gets Response details on providing FeedbackId", notes = "")
	public ResponseEntity<List<CustomResponeMasterDto>> getResponseByFeedbackId(
			@Min(value = 1, message = "{feedbackid.not.empty}") @PathVariable(value = "feedbackId") @ApiParam(value = "Accepts Only Integer") int feedbackId) {

		try {

			List<CustomResponeMasterDto> response = service.findByFeedbackId(feedbackId);

			if (response.isEmpty()) {

				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(response);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "{typeId}/{id}/{feedbackBy}")
	@ApiOperation(value = "Gets Response details on providing Id and FeedbackBy", notes = "")
	public ResponseEntity<List<ResponseMaster>> getByFeedbackIdAndFeedbackBy(
			@Min(value = 1, message = "{feedbackid.not.empty}") @PathVariable(value = "typeId") @ApiParam(value = "Accepts Only Integer") int typeId,
			@Min(value = 0, message = "{feedbackid.not.empty}") @PathVariable(value = "id") @ApiParam(value = "Accepts Only Integer") int id,
			@NotBlank(message = "{feedbackby.not.empty}") @PathVariable(value = "feedbackBy") @ApiParam(value = "UserId") String feedbackBy) {

		try {
			Optional<FeedbackMaster> feedback = service.getFeedBackByTypeAndId(typeId, id);

			if (!feedback.isPresent()) {

				return ResponseEntity.noContent().build();
			}
			List<ResponseMaster> response = service.findByFeedbackIdAndFeedbackBy(feedback.get().getFeedbackId(),
					feedbackBy);

			if (response.isEmpty()) {

				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(response);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "{typeId}/{id}")
	@ApiOperation(value = "Gets Response details on providing Id and FeedbackBy", notes = "")
	public ResponseEntity<List<CustomResponeMasterDto>> getByFeedbackTypeAndId(
			@Min(value = 1, message = "{feedbackid.not.empty}") @PathVariable(value = "typeId") @ApiParam(value = "Accepts Only Integer") int typeId,
			@Min(value = 0, message = "{feedbackid.not.empty}") @PathVariable(value = "id") @ApiParam(value = "Accepts Only Integer") int id) {

		try {
			Optional<FeedbackMaster> feedback = service.getFeedBackByTypeAndId(typeId, id);

			if (!feedback.isPresent()) {
				return ResponseEntity.noContent().build();
			}
			// getting feedback attempted user

			List<CustomResponeMasterDto> response = service.findByFeedbackId(feedback.get().getFeedbackId());

			if (response.isEmpty()) {

				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(response);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Saves Responses")
	public ResponseEntity<TypeMaster> addType(@Valid @RequestBody List<ResponesMasterDto> responseMasterDto) {

		try {

			service.addResponses(responseMasterDto);

			logger.info(NEW_RESPONSE_LOG, "Saved Responses Successfully");

			return ResponseEntity.status(HttpStatus.CREATED).build();

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "questionsummary/{type_id}/{Id}")
	public ResponseEntity<List<ResponseCount>> getFeedbackSummary(@PathVariable(value = "type_id") int tid,
			@PathVariable(value = "Id") int id) {

		return ResponseEntity.ok(service.getFeedBackSummary(tid, id));
	}

}
