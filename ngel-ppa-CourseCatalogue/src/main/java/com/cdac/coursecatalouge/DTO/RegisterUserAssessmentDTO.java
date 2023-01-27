package com.cdac.coursecatalouge.DTO;

import java.util.List;

import lombok.Data;

@Data
public class RegisterUserAssessmentDTO {
	
     String userKey;
     int applicationID;
     String courseID;
     int role;

}
