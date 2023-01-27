package com.cdac.feedback.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.feedback.dto.QuestionMasterDto;
import com.cdac.feedback.models.QuestionMaster;
import com.cdac.feedback.services.FeedbackServices;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "question")
public class QuestionController {

	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

	private static final String NEW_TYPE_LOG = "New Question was created with id:{}";
	private static final String UPDATE_TYPE_LOG = "Question  was updated with id:{}";
	private static final String ERROR = "Error Occured in QuestionController:{}";

	@Autowired
	FeedbackServices service;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Saves Question Master details ")
	public ResponseEntity<QuestionMaster> addQuestion(@Valid @RequestBody QuestionMasterDto questionMasterDto) {

		try {

			QuestionMaster questionMaster = service.addQuestion(questionMasterDto);

			logger.info(NEW_TYPE_LOG, questionMaster.getQuestionId());

			return ResponseEntity.status(HttpStatus.CREATED).body(questionMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update QuestionMaster details ")
	public ResponseEntity<QuestionMaster> updateQuestion(@Valid @RequestBody QuestionMasterDto questionMasterDto) {

		try {
			List<Integer> questionIdList = new ArrayList<>();
			questionIdList.add(questionMasterDto.getQuestionId());

			List<QuestionMaster> questionMaster = service.getQuestionByIds(questionIdList);

			if (questionMaster.isEmpty()) {

				return ResponseEntity.noContent().build();
			}

			QuestionMaster updatedQuestionMaster = service.updateQuestion(questionMasterDto);

			logger.info(UPDATE_TYPE_LOG, updatedQuestionMaster.getQuestionId());

			return ResponseEntity.ok().body(updatedQuestionMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping(value = "{questionId}")
	@ApiOperation(value = "Deletes Question on providing questionId", notes = "")
	public ResponseEntity<QuestionMaster> deleteQuestionById(
			@Min(value = 1, message = "{questionid.not.empty}") @PathVariable(value = "questionId") @ApiParam(value = "Accepts Only Integer") int questionId) {

		try {
			List<Integer> questionIdList = new ArrayList<>();
			questionIdList.add(questionId);

			List<QuestionMaster> questionMaster = service.getQuestionByIds(questionIdList);

			if (questionMaster.isEmpty()) {

				return ResponseEntity.noContent().build();
			}

			service.deleteQuestionById(questionId);

			return ResponseEntity.ok().build();

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping
	@ApiOperation(value = "Gets list of question details on entering list of question Ids", notes = "")
	public ResponseEntity<List<QuestionMaster>> getQuestionDetails(
			@Valid @NotEmpty(message = "{questionid.not.empty}") @ApiParam(value = "Accepts Only Array of Integers") @RequestParam List<Integer> questionIds) {

		try {

			List<QuestionMaster> questionMaster = service.getQuestionByIds(questionIds);

			return ResponseEntity.ok().body(questionMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	
	
	@GetMapping(value = "/getQuestionByType/{typeId}")
	public ResponseEntity<List<QuestionMaster>> getQuestionByType(	@Min(value = 1, message = "{typeid.not.empty}") @PathVariable(value = "typeId") @ApiParam(value = "Accepts Only Integer") int typeId) {

		try {

			List<QuestionMaster> questionMaster = service.getQuestionByTypeId(typeId);

			return ResponseEntity.ok().body(questionMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	@GetMapping(value = "/getQuestionByTypeAndUpdatedBy/{typeId}/{updatedBy}")
	public ResponseEntity<List<QuestionMaster>> getQuestionByTypeAndUpdatedBy(	@Min(value = 1, message = "{typeid.not.empty}") @PathVariable(value = "typeId") @ApiParam(value = "Accepts Only Integer") int typeId, @PathVariable @Pattern(regexp="^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$",message="userid  pattern not matching ") String updatedBy) {

		try {

			List<QuestionMaster> questionMaster = service.getQuestionByTypeIdAndUpdatedBy(typeId, updatedBy);

			return ResponseEntity.ok().body(questionMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	/*@GetMapping(value = "/getAllQuestionDetails")
	public ResponseEntity<List<QuestionMaster>> getAllQuestionDetails() {

		try {

			List<QuestionMaster> questionMaster = service.getAllQuestionDetails();

			return ResponseEntity.ok().body(questionMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}*/

	// Created for Meghsikshak 2.0 ; ignore or remove it if not using 
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/feedAndQuestion")

	@ApiOperation(value = "Created Feedback and Question Master details ")
	public ResponseEntity<QuestionMaster> addFeedbackAndQuestion(
			@Valid @RequestBody QuestionMasterDto questionMasterDto) {

		try {

			service.addFeedabackAndQuestion(questionMasterDto);

			// logger.info(NEW_TYPE_LOG, questionMaster.getQuestionId());

			return ResponseEntity.status(HttpStatus.CREATED).build();

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
