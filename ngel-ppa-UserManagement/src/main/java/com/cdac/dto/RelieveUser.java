package com.cdac.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class RelieveUser {

	//private String realmname;
	private String existingrolename;
	private String rolename;
	private String username;
}
