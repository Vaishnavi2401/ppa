package com.cdac.courseorg.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdac.courseorg.dtos.TenantCourseDetailsDTO;

@FeignClient(name = "course-catalouge-client", url = "${feignurl}")
public interface CourseCatalougeClient {

	@PostMapping("/addtenantCourseDetails")
	public void getCourseDetails(@RequestBody TenantCourseDetailsDTO tenantCourseDetailsDTO,@RequestParam String userId);

	@PostMapping("/updateTenantCourseDetails")
	public void updateCourseDetails(@RequestBody TenantCourseDetailsDTO tenantCourseDetailsDTO);
	
	

}
