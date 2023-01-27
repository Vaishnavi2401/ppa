package com.cdac.feedback.dto;



import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class ResponesMasterDto {
	

	@Valid
	@ApiModelProperty(value = "Length must be between 1 to 500. Must have atleast one alphabets. Special characters like  <,>,&,%  are not allowed")
	@NotBlank(message= "{feedbackresponse.notempty}")
	@Size(min=1,max = 500,message = "{feedbackresponse.maxmin}")
	@Pattern(regexp ="^[\\w\\s~@#$^*+=`|{}:;!.,?\\\"()\\[\\]-]+$",message="{feedbackresponse.pattern}")
	private String feedbackResponse;

	@Valid
	@ApiModelProperty(value = "Accepts only Integer")
	@Min(value=1,message="{feedbackid.not.empty}")
	private int typeId;
	
	@Valid
	@ApiModelProperty(value = "Accepts only Integer")
	@Min(value=0,message="{feedbackid.not.empty}")
	private int id;
	
	@Valid
	@ApiModelProperty(value = "Accepts only Integer")
	@Min(value=1,message="{questionid.not.empty}")
	private int questionId;

	@Valid
	@NotBlank(message= "{feedbackby.notempty}")
	private String feedbackBy;
}
