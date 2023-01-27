package com.cdac.coursecatalouge.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "meghsikshak-client", url = "http://localhost/MeghSikshak_NGEL/")
public interface MeghSikshakClient {

	
	@PostMapping("/NGELCourseCompletionCertificate")
	byte[] getUserDetails(@RequestParam("tenantID") String tenantID,@RequestParam("courseID") String courseID , @RequestParam("emailID") String emailID,@RequestParam("userName") String userName);
		
}
