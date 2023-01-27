package com.cdac.coursecatalouge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.coursecatalouge.feign.MeghSikshakClient;

@RestController
@RequestMapping(value = "/courses")
@CrossOrigin("*")
public class CertificateController {

	@Autowired
	MeghSikshakClient meghClient;

    @PostMapping 
    ResponseEntity<Resource> getCertificate(@RequestParam String tenantId,@RequestParam String courseId,@RequestParam String emailId,@RequestParam String name) {
    	
    	
    	if(tenantId==null || courseId==null || emailId==null || name==null) {
    		
    		return ResponseEntity.noContent().build();
    	}
    	

    	
    byte[] pdf = 	meghClient.getUserDetails(tenantId, courseId, emailId, name);
    
    ByteArrayResource resource = new ByteArrayResource(pdf);

    
    HttpHeaders header = new HttpHeaders();
    header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.pdf");
    header.add("Cache-Control", "no-cache, no-store, must-revalidate");
    header.add("Pragma", "no-cache");
    header.add("Expires", "0");
    
    		
    	return ResponseEntity.status(HttpStatus.OK)
    			.headers(header)
    			 .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
