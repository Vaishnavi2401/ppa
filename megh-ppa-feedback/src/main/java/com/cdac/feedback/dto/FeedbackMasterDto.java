package com.cdac.feedback.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class FeedbackMasterDto {

	// if adding feature for allowing indian languages consider adding these regexes support for devangari,urdu,kannada,malayalam,tamil,gaumiki,bengali,oriya
	//[\u0C80-\u0CFF \u0900-\u097F \u0C00-\u0C7F \u0D00-\u0D7F \u0B80-\u0BFF \u0B00-\u0B7F \u0A81-\u0A82 \u0980-\u09FF \u0600-\u06FF]
	@ApiModelProperty(value = "Not compulsary while saving")
	private int feedbackId;
	
	@ApiModelProperty(value = "Length must be between 3 to 500. Must have atleast one alphabets. Special characters like  <,>,&,%  are not allowed")
	@NotBlank(message= "{feedback.description.notempty}")
	@Size(min=3,max = 500,message = "{feedback.description.maxmin}")
	@Pattern(regexp ="^(?=.*[a-zA-Z])[\\w\\s~@#$^*+=`|{}:;!.,?\\\"()\\[\\]-]+$",message="{feedback.descritpion.pattern}")
	private String description;
	
	@ApiModelProperty(value = "Length must be between 3 to 50. Must have atleast one alphabets. Allowed only Alphabets, Numbers, Hypen(-), Underscore(_)")
	@NotBlank(message= "{feedback.title.notempty}")
	@Pattern(regexp ="^(?=.*[a-zA-Z])[\\w\\s-]+$",message="{feedback.title.pattern}")
	@Size(min=3,max = 50,message = "{feedback.title.maxmin}")
	private String feedbackTitle;
	
	@ApiModelProperty(value = "Accepts only Integer")
	@Min(value=1,message="{typeid.not.empty}")
	private int typeMasterId;
	
	@ApiModelProperty(value = "Accepts only Integer")
	@Min(value=1,message="{id.not.empty}")
	private int id;

	@NotBlank(message= "{updatedby.notempty}")
	private String updatedBy;


	
}
