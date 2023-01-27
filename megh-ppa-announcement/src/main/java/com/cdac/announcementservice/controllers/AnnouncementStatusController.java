package com.cdac.announcementservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.announcementservice.models.Announcement;
import com.cdac.announcementservice.models.AnnouncementReadStatus;
import com.cdac.announcementservice.services.AnnouncementService;
import com.cdac.announcementservice.services.AnnouncementStatusService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/announcements/status/")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class AnnouncementStatusController extends Controller<AnnouncementReadStatus> {

	private final AnnouncementStatusService stausService = new AnnouncementStatusService();

	@ApiOperation(value = "Saving Announcement")
	@PostMapping("insertReadStatus/")
	public ResponseEntity<AnnouncementReadStatus> save(@RequestBody AnnouncementReadStatus announcement) {
		return responseOk(stausService.create(announcement));
	}
}
