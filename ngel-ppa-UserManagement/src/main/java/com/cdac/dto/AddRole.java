package com.cdac.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class AddRole {

	private String roleName;
    private String realmName;
    
    
}
