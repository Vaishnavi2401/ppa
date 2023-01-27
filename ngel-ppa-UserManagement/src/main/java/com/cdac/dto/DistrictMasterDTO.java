package com.cdac.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class DistrictMasterDTO {
	
	private int districtId;
	private int stateId;
	private String districtName;

}
