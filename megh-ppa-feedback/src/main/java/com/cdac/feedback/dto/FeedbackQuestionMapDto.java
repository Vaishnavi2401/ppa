package com.cdac.feedback.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FeedbackQuestionMapDto {
	
	@ApiModelProperty(value = "Accepts Only Integer ")
	@Min(value=1,message="{feedbackid.not.empty}")
		int feedbackId;
	
	@ApiModelProperty(value = "Accepts Only Array of Integer ")
	@NotEmpty(message="{questionid.not.empty}")
	@Valid
		List<Integer>questionId;

}
