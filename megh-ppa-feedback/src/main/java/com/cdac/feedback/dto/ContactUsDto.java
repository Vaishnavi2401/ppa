package com.cdac.feedback.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContactUsDto {

	@Email(message = "{contactus.email}")
	private String emailId;

	@ApiModelProperty(value = "Length must be between 3 to 2000. Must have atleast one alphabets. Special characters like  <,>,&,%  are not allowed")
	@NotBlank(message= "{contactus.message.notempty}")
	@Size(min=3,max = 2000,message = "{contactus.message.maxmin}")
	@Pattern(regexp ="^(?=.*[a-zA-Z])[\\w\\s~@#$^*+=`|{}:;!.,?\\\"()\\[\\]-]+$",message="{contactus.message.pattern}")
	private String message;

	
	@ApiModelProperty(value = "Length must be between 3 to 50. Must have atleast one alphabets. Allowed only Alphabets, Numbers, Hypen(-), Underscore(_)")
	@NotBlank(message= "{contactus.name.notempty}")
	@Pattern(regexp ="^(?=.*[a-zA-Z])[\\w\\s-]+$",message="{contactus.name.pattern}")
	@Size(min=3,max = 50,message = "{contactus.name.maxmin}")
	private String name;
	
	
	
	@ApiModelProperty(value = "Length must be between 3 to 150. Must have atleast one alphabets. Special characters like  <,>,&,%  are not allowed")
	@NotBlank(message= "{contactus.subject.notempty}")
	@Pattern(regexp ="^(?=.*[a-zA-Z])[\\w\\s~@#$^*+=`|{}:;!.,?\\\"()\\[\\]-]+$",message="{contactus.subject.pattern}")
	@Size(min=3,max = 150,message = "{contactus.subject.maxmin}")

	private String subject;
	
}
