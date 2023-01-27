package com.cdac.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.context.annotation.Configuration;

import com.cdac.model.RankMaster;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class UserMasterDTO {
	
	
	@NotBlank(message="User Name cannot be null")
	@Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String username;
	
	
	@NotBlank(message="email cannot be null")
	@Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String email;

	
	@NotBlank(message="firstName cannot be null")
	@Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String firstName;

	@NotBlank(message="LastName cannot be null")
	@Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String lastName;

	
	
	private long mobile;
	
	@NotNull(message="rankId cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Enter Only Number ")
	private int rankId;

	private int orgId;
	
	
	@NotBlank(message="serviceNo cannot be null")
	@Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Invalid Format Only Alphabets & Numbers")
	private String serviceNo;
	
	private String roleName;
	
	//As there was different object for rankId while setting and getting below line has been added
	RankMaster rankMaster;
	
	@NotBlank(message="Updated By  cannot be null")
	private String updateBy;
}
