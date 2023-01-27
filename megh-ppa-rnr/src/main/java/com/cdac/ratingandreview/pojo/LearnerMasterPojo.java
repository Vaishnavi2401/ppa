package com.cdac.ratingandreview.pojo;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class LearnerMasterPojo {

	private String learnerUsername;
	
	private String address;

	private String city;
	
	private Date dob;

	private String eduQualification;

	private String email;

	private String firstName;

	private String gender;

	private String instituteName;

	private String lastName;

	private  long mobile;

	private String officialAddress;

	private int pincode;

	private String status;

	private int countryId;
	
	private String countryName;
		
	private int stateId;
	
	private String stateName;
	
	private int districtId;

	private String districtName;
	
	private String updatedBy;
}
