package com.cdac.dto;

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
public class MeghUserEditProfile {

	@NotBlank(message = "Instructor Username cannot be null")
    @Pattern(regexp = "^[A-Za-z0-9- ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String learnerUsername;

	@NotBlank(message = "Instructor address cannot be blank")
    @Pattern(regexp = "^[A-Za-z-.#0-9, ]+$", message = "Invalid Format Alphabets and - . , # only")
	private String address;

	@NotBlank(message = "Instructor city cannot be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets ")
	private String city;

	@NotBlank(message = "Instructor date of birth cannot be blank")
	@JsonFormat(pattern="dd-MM-yyyy")
	private String dob;

	@NotBlank(message = "Instructor education qualification cannot be blank")
    @Pattern(regexp = "^[A-Za-z-. ]+$", message = "Invalid Format Alphabets and - . only")
	private String eduQualification;

	@NotBlank(message = "Instructor email cannot be blank")
	@Email
	private String email;

	@NotBlank(message = "Instructor first name cannot be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets")
	private String firstName;

	@NotBlank(message = "Learner gender be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets")
	private String gender;

	@NotBlank(message = "Instructor last name cannot be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Invalid Format Only Alphabets")
	private String lastName;
	
	@NotBlank(message = "Instructor institute cannot be blank")
    @Pattern(regexp = "^[A-Za-z-. ]+$", message = "Invalid Format Alphabets and - . only")
	private String instituteName;

	@NotBlank(message = "Instructor mobile cannot be null")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Invalid Format Only 10 digit number")
	private  long mobile;

	@NotBlank(message = "Instructor pincode cannot be null")
   	@Pattern(regexp="(^$|[0-9]{6})", message = "Invalid Format Only 6 digit number")
	private int pincode;

	private String status;

	@NotBlank(message = "Instructor country cannot be null")
   	@Pattern(regexp="(^$|[0-9]{1})", message = "Invalid Format Only 1 digit number")
	private int countryId;
	
	@NotBlank(message = "Instructor state cannot be null")
	@Size(min=1,max=2,message = "minimum 1 digit and max 2 digit state id")
   	@Pattern(regexp="(^$|[0-9])", message = "Invalid Format Only 1 digit number")
	private int stateId;
	
	@NotBlank(message = "Instructor district cannot be null")
	@Size(min=1,max=3,message = "minimum 1 digit and max 3 digit state id")
   	@Pattern(regexp="(^$|[0-9])", message = "Invalid Format Only 3 digit number")
	private int districtId;
	
	@NotBlank(message = "Instructor designation cannot be blank")
	private String  designation;
	
	@NotBlank(message = "Instructor biodata cannot be blank")
	private String  biodata;
	
	@NotBlank(message = "Instructor skypeId cannot be blank")
	private String  skypeId;
	
	@NotBlank(message = "Instructor facebookId cannot be blank")
	private String  facebookId;
	
	@NotBlank(message = "Instructor twitterId cannot be blank")
	private String  twitterId;
	
	@NotBlank(message = "Instructor linkedinId cannot be blank")
	private String  linkedinId;
	
	@NotBlank(message = "Instructor youtubeId cannot be blank")
	private String  youtubeId;
	
	
	private String updatedBy;
}
