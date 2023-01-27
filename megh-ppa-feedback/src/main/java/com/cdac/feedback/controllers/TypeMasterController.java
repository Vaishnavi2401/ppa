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

import com.cdac.feedback.dto.TypeMasterDto;
import com.cdac.feedback.models.TypeMaster;
import com.cdac.feedback.services.FeedbackServices;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "type", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeMasterController {

	private static final Logger logger = LoggerFactory.getLogger(TypeMasterController.class);

	private static final String NEW_TYPE_LOG = "New Type was created with id:{}";
	private static final String UPDATE_TYPE_LOG = "Type was updated with id:{}";
	private static final String ERROR = "Error Occured in TypeController:{}";

	@Autowired
	FeedbackServices service;
	
	
	
	
	@GetMapping()
	@ApiOperation(value = "Gets ALL TypeMaster details", notes = "")
	public ResponseEntity<List<TypeMaster>> getAllTypeMasterDetails() {

		try {

		List<TypeMaster> typeMaster = service.getAllTypeMasterDetails();
				
			return ResponseEntity.ok(typeMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	

	@GetMapping(value = "{typeId}")
	@ApiOperation(value = "Gets TypeMaster details on providing TypeId", notes = "")
	public ResponseEntity<TypeMaster> getTypeById(
			@Min(value=1,message = "{typeid.not.empty}") @PathVariable(value = "typeId") @ApiParam(value = "Accepts Only Integer") int id) {

		try {

			final Optional<TypeMaster> typeMaster = service.getTypeById(id);

			if (typeMaster.isEmpty()) {

				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(typeMaster.get());

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Saves TypeMaster details ")
	public ResponseEntity<TypeMaster> addType(@Valid @RequestBody TypeMasterDto typeMasterPojo) {

		try {
			
			//Checking if the name is already there in Database
			
			if(typeMasterPojo.getTypeTitle()!=null && service.getTypeByName(typeMasterPojo.getTypeTitle())) {
				
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			
			

			TypeMaster typeMaster = service.addTypeMaster(typeMasterPojo);

			logger.info(NEW_TYPE_LOG, typeMaster.getTypeMasterId());

			return ResponseEntity.status(HttpStatus.CREATED).body(typeMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update TypeMaster details ")
	public ResponseEntity<TypeMaster> updateType(@Valid @RequestBody TypeMasterDto typeMasterDto) {

		try {

			Optional<TypeMaster> typeMaster = service.getTypeById(typeMasterDto.getTypeMasterId());

			if (typeMaster.isEmpty()) {

				return ResponseEntity.noContent().build();
			}

			TypeMaster updatedTypeMaster = service.updateTypeMaster(typeMasterDto);

			logger.info(UPDATE_TYPE_LOG, updatedTypeMaster.getTypeMasterId());

			return ResponseEntity.ok().body(updatedTypeMaster);

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@DeleteMapping(value = "{typeId}")
	@ApiOperation(value = "Deletes TypeMaster on providing TypeId", notes = "")
	public ResponseEntity<TypeMaster> deleteTypeById(
			@Min(value =1,message = "{typeid.not.empty}") @PathVariable(value = "typeId") @ApiParam(value = "Accepts Only Integer") int typeId) {

		try {

			final Optional<TypeMaster> typeMaster = service.getTypeById(typeId);

			if (typeMaster.isEmpty()) {

				return ResponseEntity.noContent().build();
			}

			service.deleteTypeById(typeId);
			
			return ResponseEntity.ok().build();

		} catch (Exception e) {

			logger.error(ERROR, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	
	

}
