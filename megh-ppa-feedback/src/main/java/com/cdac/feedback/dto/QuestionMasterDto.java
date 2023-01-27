package com.cdac.feedback.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cdac.feedback.configs.EnumNamePattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;





@Data
public class QuestionMasterDto {


	@ApiModelProperty(value = "Not compulsary while saving")
	private int questionId;

	@ApiModelProperty(value = "Allowed only true and false")
	@NotBlank(message= "{mandatory.notempty}")
	@EnumNamePattern(acceptedValues = {"true","false"},message = "{mandatory.pattern}")
	private String mandatory;


	@ApiModelProperty(value = "Length must be between 3 to 500. Must have atleast one alphabets. Special characters like  <,>,&,%  are not allowed")
	@NotBlank(message= "{question.notempty}")
	@Size(min=3,max = 500,message = "{question.maxmin}")
	@Pattern(regexp ="^(?=.*[a-zA-Z])[\\w\\s~@#$^*+=`|{}:;!.,?\\\"()\\[\\]-]+$",message="{question.pattern}")
	private String question;

	@ApiModelProperty(value = "Accepts only TF,TA,SC,MC values")
	@NotBlank(message= "{question.type.notempty}")
	@EnumNamePattern(acceptedValues = {"TF","TA","SC","MC"},message = "{questiontype.pattern}")
	private String questionType; 
	
	@ApiModelProperty(value = "Accepts List of Strings ")
	@Valid
	@NotEmpty(message= "{question.option.notempty}")
	private List<@Pattern(regexp ="^(?=.*[a-zA-Z])[\\w\\s~@#$^*+=`|{}:;!.,?\\\"()\\[\\]-]+$",message="{question.option.pattern}") String> options;
	

	@NotBlank(message= "{updatedby.notempty}")
	private String updatedBy;
	
	@ApiModelProperty(value = "Type Master Id")
	@Min(1)
	private int typeId;
	
	//temp
	
	private int courseId;
	
	private int tenantId;
}



