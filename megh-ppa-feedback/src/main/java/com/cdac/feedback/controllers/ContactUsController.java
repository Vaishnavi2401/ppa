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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.feedback.dto.ContactUsDto;
import com.cdac.feedback.dto.FeedbackMasterDto;
import com.cdac.feedback.models.ContactMaster;
import com.cdac.feedback.models.FeedbackMaster;
import com.cdac.feedback.services.ContactUsServices;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "contactus")
public class ContactUsController {

@Autowired
ContactUsServices contactUsService;

  private static final Logger logger = LoggerFactory.getLogger(ContactUsController.class);

  private static final String NEW_CONTACTUS_LOG = "New Contact Us Details Created  :{}";
  private static final String ERROR = "Error Occured in ContactUs Controller:{}";

@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(value = "Saves ContactUs details ")
public ResponseEntity<ContactMaster> addFeedback(@Valid @RequestBody ContactUsDto contactUsDto) {

    try {

      ContactMaster contactMaster = contactUsService.addContactUs(contactUsDto);

      logger.info(NEW_CONTACTUS_LOG, contactMaster.getId());

      return ResponseEntity.status(HttpStatus.CREATED).body(contactMaster);

    } catch (Exception e) {

      logger.error(ERROR, e.getMessage());

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
  }

  @GetMapping()
  @ApiOperation(value = "Gets All ContactUs details")
  public ResponseEntity<List<ContactMaster>> getAllContactUsDetails() {

    try {
      return ResponseEntity.ok().body(contactUsService.getAllContactUsDetails());

    } catch (Exception e) {

      logger.error(ERROR, e.getMessage());

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

}
