package com.cdac.feedback.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class TypeMasterDto {

    
	private int typeMasterId;

	@ApiModelProperty(value = "Length must be between 3 to 500. Must have atleast one alphabets. Special characters like  <,>,&,%  are not allowed")
	@NotBlank(message= "{type.description.notempty}")
	@Size(min=3,max = 500,message = "{type.description.maxmin}")
	@Pattern(regexp ="^(?=.*[a-zA-Z])[\\w\\s~@#$^*+=`|{}:;!.,?\\\"()\\[\\]-]+$",message="{type.descritpion.pattern}")
	private String typeDescription;
    
	
	@ApiModelProperty(value = "Length must be between 3 to 50. Must have atleast one alphabets. Allowed only Alphabets, Numbers, Hypen(-), Underscore(_)")
    @NotBlank(message= "{type.title.notempty}")
	@Pattern(regexp ="^(?=.*[a-zA-Z])[\\w\\s-]+$",message="{type.title.pattern}")
	@Size(min=3,max = 50,message = "{type.title.maxmin}")
  	private String typeTitle;
    
    
    @NotBlank(message= "{updatedby.notempty}")
	private String updateBy;
	
}
