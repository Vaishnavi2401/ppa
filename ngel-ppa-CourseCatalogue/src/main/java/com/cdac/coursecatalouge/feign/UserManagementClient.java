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
import com.cdac.coursecatalouge.DTO.LearnerMasterLitePojo;



@FeignClient(name = "usermanagement-client", url = "${feignurl}")

public interface UserManagementClient {

	

	@GetMapping("/um_api/getlearner/{userId}")
	//@GetMapping("/api/learner/{userId}")
	LearnerMasterLitePojo getUserDetails(@PathVariable("userId") String userId);

	@GetMapping("/um_api/learner/getmeghinst")
	List<LearnerMasterDTO> getInstructorDetails(@RequestParam("searchkey") String[] instIds);
	
	@PostMapping("/um_api/SendEmailNotifofCourseEnroll")
	void sendEmailforCourseEnrollStatus(@RequestParam("userId") String userId, @RequestParam("cname") String cname,@RequestParam("status") int status);
	
}
