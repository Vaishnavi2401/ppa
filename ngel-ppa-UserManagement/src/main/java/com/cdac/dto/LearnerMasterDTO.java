package com.cdac.dto;

import java.util.Date;

import org.springframework.context.annotation.Configuration;

import com.cdac.model.CountryMaster;
import com.cdac.model.DistrictMaster;
import com.cdac.model.StateMaster;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class LearnerMasterDTO {

	private String learnerUsername;
	private int programId;
	private String address;

	private String city;

	@JsonFormat(pattern="dd-MM-yyyy")
	private String dateServiceEntry;

	@JsonFormat(pattern="dd-MM-yyyy")
	private String dob;

	private String eduQualification;

	private String email;

	private String firstName;

	private String gender;

	private String instituteName;

	private String lastName;

	private  long mobile;

	private String officialAddress;

	private int pincode;

	private String placePosting;

	private String serviceNo;

	private String status;

	private int rankId;

	private CountryMaster countryMaster;
	
	private StateMaster stateMaster;
	
	private DistrictMaster districtMaster;
	
	private String updatedBy;
}
