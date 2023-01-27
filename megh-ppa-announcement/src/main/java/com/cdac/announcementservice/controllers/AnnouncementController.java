package com.cdac.announcementservice.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.announcementservice.models.Announcement;
import com.cdac.announcementservice.models.AnnouncementLog;
import com.cdac.announcementservice.models.ApplicationMaster;
import com.cdac.announcementservice.services.AnnouncementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/announcements/")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@Api(value = "Announcement REST Endpoint", description = "Shows the announcement info")
public class AnnouncementController extends Controller<Announcement> {

	private final AnnouncementService service;

	@ApiOperation(value = "Returns All Announcements List")
	@GetMapping()
	public ResponseEntity<List<Announcement>> list() {
		return responseOk(service.findAll());
	}

	@RequestMapping(path = { "/getAnnouncementByCurrentDateTopublishUpTo" }, method = { RequestMethod.GET })
	public ResponseEntity<List<Announcement>> findByCurrentDatetoPublishupto() {
		return responseOk(service.findByCurrentDateto());
	}

	@ApiOperation(value = "Returns  Announcements list By Announcement id ")
	@GetMapping("{id}")
	public ResponseEntity<Announcement> findById(@PathVariable("id") int id) {
		return service.findById(id).map(this::responseOk).orElse(responseNotFound());
	}

	@ApiOperation(value = "Returns  Announcements list By  course id")
	@GetMapping("course/{id}")
	public ResponseEntity<List<Announcement>> findByCourse(@PathVariable("id") String id) {
		return responseOk(service.findAllByCourse(id));
	}

	@ApiOperation(value = "Returns  Announcements list By Author")
	@GetMapping("author/{id}")
	public ResponseEntity<List<Announcement>> findByAurthor(@PathVariable("id") String id) {
		return responseOk(service.findAllByAuthor(id));
	}

	@ApiOperation(value = "Returns  Announcements list between two dates")
	@RequestMapping(path = "/getAnnouncementsByRange/{sdate}/{edate}", method = RequestMethod.GET)
	public ResponseEntity<List<Announcement>> findByRange(@PathVariable("sdate") String sdate,
			@PathVariable("edate") String edate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateBefore = formatter.parse(sdate);
		Date dateAfter = formatter.parse(edate);
		return responseOk(service.findAllByRange(dateBefore, dateAfter));
	}

	@ApiOperation(value = "Saving Announcement")
	@PostMapping("saveAnnouncement")
	public ResponseEntity<Announcement> save(@RequestBody Announcement announcement) {
		return responseOk(service.create(announcement));
	}

	@ApiOperation(value = "Updating Announcement by announcement id")
	@PutMapping("{id}")
	public ResponseEntity<Announcement> update(@PathVariable("id") int id, @RequestBody Announcement announcement) {
		return service.update(announcement, id).map(this::responseOk).orElse(responseNotFound());
	}

	@ApiOperation(value = "deleting Announcement by announcement id")
	@DeleteMapping("{id}")
	public ResponseEntity<Announcement> delete(@PathVariable("id") int id) {
		return service.delete(id).map(this::responseOk).orElse(responseNotFound());
	}

	@ApiOperation(value = "Returns All General Announcements list By Author")
	@GetMapping("getAllGeneralAnnouncementListByAuthor/{authorId}")
	public ResponseEntity<List<Announcement>> findAllGeneralAnnouncementByAuthor(
			@PathVariable("authorId") String author) {
		return responseOk(service.findAllGeneralAnnouncementByAuthor(author));
	}

	@ApiOperation(value = "Returns All course Announcements list By Author")
	@GetMapping("getAllCourseAnnouncementListByAuthor/{authorId}/{courseId}")
	public ResponseEntity<List<Announcement>> findAllCourseAnnouncementByAurthor(
			@PathVariable("authorId") String author, @PathVariable("courseId") String courseId) {
		return responseOk(service.findAllCourseAnnouncementByAuthor(author, courseId));
	}

	@ApiOperation(value = "Returns current General Announcements list By Author")
	@GetMapping("getCurrentGeneralAnnouncementListByAuthor/{authorId}")
	public ResponseEntity<List<Announcement>> findCurrentGeneralAnnouncementByAuthor(
			@PathVariable("authorId") String author) {
		return responseOk(service.findCurrentGeneralAnnouncementByAuthor(author));
	}

	@ApiOperation(value = "Returns Current course Announcements list By Author")
	@GetMapping("getCurrentCourseAnnouncementListByAuthor/{authorId}/{courseId}")
	public ResponseEntity<List<Announcement>> findCurrentCourseAnnouncementByAurthor(
			@PathVariable("authorId") String author, @PathVariable("courseId") String courseId) {
		return responseOk(service.findCurrentCourseAnnouncementByAuthor(author, courseId));
	}
}
