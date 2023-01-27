package com.cdac.announcementservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.announcementservice.models.ApplicationMaster;
import com.cdac.announcementservice.services.AnnouncementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/announcements/master/")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@Api(value = "Announcement REST Endpoint", description = "Shows the tenant info")
public class AnnouncementMasterController extends Controller<ApplicationMaster> {
	private final AnnouncementService service;

	@ApiOperation(value = "createTenantDB")
	@PostMapping("createTenantDB")
	public ResponseEntity<ApplicationMaster> save(@RequestBody ApplicationMaster appMaster) {
		System.out.println("---------enterd in contoller----");
		return responseOk(service.create(appMaster));
	}
}
