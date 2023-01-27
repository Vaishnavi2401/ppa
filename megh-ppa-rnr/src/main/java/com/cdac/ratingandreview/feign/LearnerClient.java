package com.cdac.ratingandreview.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdac.ratingandreview.pojo.LearnerMasterPojo;



@FeignClient(name = "learner-client", url = "http://localhost")
//@FeignClient(name = "learner-client", url = "http://localhost:8084")
public interface LearnerClient {
	
@GetMapping("/um_api/learner/byId")
LearnerMasterPojo getDetailsofLearner(@RequestParam("userid") String userid);
	

}
