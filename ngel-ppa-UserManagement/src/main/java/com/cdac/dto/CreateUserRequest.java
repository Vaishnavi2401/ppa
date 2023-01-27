package com.cdac.dto;

import org.springframework.context.annotation.Configuration;



import lombok.Getter;
import lombok.Setter;

/**
 * @author Ramu Parupalli
 */
@Setter
@Getter
@Configuration
public class CreateUserRequest {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String companyName;
}
