package com.cdac.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class StateMasterDTO {

	private int countryId;
	private int stateId;
	private String stateName;
}
