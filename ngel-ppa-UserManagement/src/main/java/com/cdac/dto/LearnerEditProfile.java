package com.cdac.dto;

import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class LearnerEditProfile {

	@NotBlank(message = "Learner Username cannot be null")
	@Pattern(regexp = "^[A-Za-z0-9- ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String learnerUsername;

	@NotBlank(message = "Learner address cannot be blank")
	@Pattern(regexp = "^[A-Za-z-.#0-9, ]+$", message = "Invalid Format Alphabets and - . , # only")
	private String address;

	@NotBlank(message = "Learner city cannot be blank")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets ")
	private String city;

	@NotBlank(message = "Learner date of birth cannot be blank")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private String dob;

	@NotBlank(message = "Learner education qualification cannot be blank")
	@Pattern(regexp = "^[A-Za-z-. ]+$", message = "Invalid Format Alphabets and - . only")
	private String eduQualification;

	@NotBlank(message = "Learner email cannot be blank")
	@Email
	private String email;

	@NotBlank(message = "Learner first name cannot be blank")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets")
	private String firstName;

	@NotBlank(message = "Learner gender be blank")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets")
	private String gender;

	@NotBlank(message = "Learner last name cannot be blank")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets")
	private String lastName;

	@NotBlank(message = "Learner institute cannot be blank")
	@Pattern(regexp = "^[A-Za-z-. ]+$", message = "Invalid Format Alphabets and - . only")
	private String instituteName;

	@NotBlank(message = "Learner mobile cannot be null")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Invalid Format Only 10 digit number")
	private long mobile;

	@NotBlank(message = "Learner pincode cannot be null")
	@Pattern(regexp = "(^$|[0-9]{6})", message = "Invalid Format Only 6 digit number")
	private int pincode;

	private String status;

	@NotBlank(message = "Learner country cannot be null")
	@Pattern(regexp = "(^$|[0-9]{1})", message = "Invalid Format Only 1 digit number")
	private int countryId;

	@NotBlank(message = "Learner state cannot be null")
	@Size(min = 1, max = 2, message = "minimum 1 digit and max 2 digit state id")
	@Pattern(regexp = "(^$|[0-9])", message = "Invalid Format Only 1 digit number")
	private int stateId;

	@NotBlank(message = "Learner district cannot be null")
	@Size(min = 1, max = 3, message = "minimum 1 digit and max 3 digit state id")
	@Pattern(regexp = "(^$|[0-9])", message = "Invalid Format Only 3 digit number")
	private int districtId;

	@NotBlank(message = "Learner designation cannot be null")
	@Size(min = 1, max = 2, message = "minimum 1 digit and max 2 digit designation id")
	@Pattern(regexp = "(^$|[0-9])", message = "Invalid Format Only 2 digit number")
	private int desgId;
	
	@NotBlank(message = "Learner Qualification cannot be null")
	@Size(min = 1, max = 2, message = "minimum 1 digit and max 2 digit Qualification id")
	@Pattern(regexp = "(^$|[0-9])", message = "Invalid Format Only 2 digit number")
	private int qualId;
	
	@NotBlank(message = "Learner Cadre cannot be null")
	@Size(min = 1, max = 2, message = "minimum 1 digit and max 2 digit Cadre id")
	@Pattern(regexp = "(^$|[0-9])", message = "Invalid Format Only 2 digit number")
	private int cadreId;
	
	private String designation;

	private String biodata;

	private String skypeId;

	private String facebookId;

	private String twitterId;

	private String linkedinId;

	private String youtubeId;

	private String updatedBy;

	private int authenticationId;

	private String beltNumber;

	private String gpfNumber;

	private String isactive;

	private String officialAddress;

	private String placeOfPosting;

	private String profilePicPath;

	private Timestamp registeredDate;

	private String updateBy;

	private Date updatedOn;

	private int userId;



}
