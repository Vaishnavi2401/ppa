package com.cdac.dto;

import java.util.List;

import lombok.Data;

/**
 * @author Ramu Parupalli
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
public class CurrentUser {

    private String userId;
    private String username;
    private String email;
    private List<String> roles;
}
