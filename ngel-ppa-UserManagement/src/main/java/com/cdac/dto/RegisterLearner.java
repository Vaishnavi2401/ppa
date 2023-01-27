package com.cdac.dto;


import java.math.BigInteger;

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
public class RegisterLearner {

	//@NotBlank(message = "Learner Username cannot be null")
    //@Pattern(regexp = "^[A-Za-z0-9- ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String learnerUsername;
	
	@NotBlank(message = "Learner email cannot be null")
	@Email
	private String email;

	@NotBlank(message = "Learner first name cannot be null")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets")
	private String firstName;

	@NotBlank(message = "Learner last name cannot be null")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets")
	private String lastName;

	@NotBlank(message = "Learner mobile cannot be null")
    @Pattern(regexp = "^[0-9]+$", message = "Invalid Format Only Numbers")
	@Pattern(regexp="^[0-9]+$", message = "Invalid Format Only 10 digit number")
	private  BigInteger mobile;
	
	@NotBlank(message = "Learner belt number cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9, ]+$", message = "Invalid Format Alphabets and digits only")
	private String beltNumber;

	@NotBlank(message = "Learner gpf number cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9, ]+$", message = "Invalid Format Alphabets and digits only")
	private String gpfNumber;
	
	@NotBlank(message = "Learner Cadre cannot be null")
	@Size(min = 1, max = 2, message = "minimum 1 digit and max 2 digit Cadre id")
	@Pattern(regexp = "(^$|[0-9])", message = "Invalid Format Only 2 digit number")
	private int cadreId;
	
	private String  placeOfPosting;
	
	@NotBlank(message = "Learner Qualification cannot be null")
	@Size(min = 1, max = 2, message = "minimum 1 digit and max 2 digit Qualification id")
	@Pattern(regexp = "(^$|[0-9])", message = "Invalid Format Only 2 digit number")
	private int qualId;
	
	@NotBlank(message = "Learner designation cannot be null")
	@Size(min = 1, max = 2, message = "minimum 1 digit and max 2 digit designation id")
	@Pattern(regexp = "(^$|[0-9])", message = "Invalid Format Only 2 digit number")
	private int desgId;
	
	@NotBlank(message = "Learner date of birth cannot be blank")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private String dob;
	
	@NotBlank(message = "Learner address cannot be blank")
	@Pattern(regexp = "^[A-Za-z-.#0-9, ]+$", message = "Invalid Format Alphabets and - . , # only")
	private String address;
	
	private String status;

	private String updatedBy;

		
	}
