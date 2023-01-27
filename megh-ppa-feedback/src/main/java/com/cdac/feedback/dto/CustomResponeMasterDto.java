package com.cdac.feedback.dto;

import java.util.List;


import com.cdac.feedback.models.ResponseMaster;

import lombok.Data;

@Data
public class CustomResponeMasterDto {
	
	String userId;
	List<ResponseMaster> responseMaster;

}
