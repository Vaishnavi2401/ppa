package com.cdac.dto;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class LearnerMasterLitePojo {

	private String learnerUsername;
	
	private Date dob;

	private String eduQualification;
	
	private String beltno;
	
	private String gpfno;
	
	private String cadre;
	
	private String designation;
	
	private String placeofposting;

	private String email;

	private String firstName;

	private String gender;

	private String lastName;

	private  long mobile;

	private String profilepicpath;
	
	private String idproofpicpath;
}
