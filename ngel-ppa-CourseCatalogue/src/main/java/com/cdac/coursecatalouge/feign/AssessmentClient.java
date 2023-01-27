package com.cdac.coursecatalouge.feign;

import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdac.coursecatalouge.DTO.LearnerMasterDTO;
import com.cdac.coursecatalouge.DTO.RegisterUserAssessmentDTO;



@FeignClient(name = "assessment-client", url = "tmis.hyderabad.cdac.in:8081")

public interface AssessmentClient {

	

	@GetMapping("/Assessment/quiz/registerAssessmentUser")
	String registerUserinAssessment(@RequestBody RegisterUserAssessmentDTO registerUser);



	 
	

}
