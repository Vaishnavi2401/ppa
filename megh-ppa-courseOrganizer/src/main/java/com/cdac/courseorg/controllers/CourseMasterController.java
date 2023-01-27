package com.cdac.courseorg.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.courseorg.dtos.CourseMasterDTO;
import com.cdac.courseorg.dtos.CourseScheduleDTO;
import com.cdac.courseorg.dtos.DirDetailsDTO;
import com.cdac.courseorg.dtos.UpdatedCourseMasterDTO;
import com.cdac.courseorg.repositories.UpdatedCourseMasterInterface;
import com.cdac.courseorg.services.CourseOrganizerServices;

@CrossOrigin("*")
@RequestMapping(value = "/courseOrganizer")
@RestController
public class CourseMasterController {

	@Autowired
	CourseOrganizerServices services;

	@PostMapping(value = "/addCourse")
	public String addCourseMaster(@RequestPart("categoryId") String categoryId,
			@RequestPart("courseFee") String courseFee, @RequestPart("courseName") String courseName,
			@RequestPart("courseType") String courseType, @RequestPart("duration") String duration,

			@RequestPart("courseAccessType") String courseAccessType,
			@RequestPart("generalDetails") String generalDetails, @RequestPart("prerequisite") String prerequisite,
			@RequestPart("objective") String objective, @RequestPart("inst_profile") String inst_profile,
			@RequestPart("fee_discount") String fee_discount, @RequestPart("commencementDate") String commencementDate,
			@RequestPart("enrollEdate") String enrollEdate, @RequestPart("enrollSdate") String enrollSdate,
			@RequestPart("isScormCompliant") String isScormCompliant, @RequestPart("publishDate") String publishDate,
			@RequestPart("userId") String userId, @RequestPart(required = false) MultipartFile file,
			@RequestPart(required = false) MultipartFile video, @RequestPart(required = false) MultipartFile banner) {

		CourseMasterDTO cMasterDTO = new CourseMasterDTO();
		CourseScheduleDTO cScheduleDTO = new CourseScheduleDTO();
		cMasterDTO.setCategoryId(Integer.parseInt(categoryId));
		cMasterDTO.setCourse_Fee(Integer.parseInt(courseFee));
		cMasterDTO.setCourse_Type(courseType);
		cMasterDTO.setCourseName(courseName);
		cMasterDTO.setDuration(Integer.parseInt(duration));
		cMasterDTO.setCourse_access_type(courseAccessType);

		cMasterDTO.setGeneralDetails(generalDetails);
		cMasterDTO.setUserId(userId);
		cMasterDTO.setIsScormCompliant(Integer.parseInt(isScormCompliant));
		cMasterDTO.setPrerequisite(prerequisite);

		cMasterDTO.setObjective(objective);
		cMasterDTO.setInst_profile(inst_profile);
		cMasterDTO.setFee_discount(Integer.parseInt(fee_discount));

		cScheduleDTO.setCommencementDate(commencementDate);
		cScheduleDTO.setEnrollEdate(enrollEdate);
		cScheduleDTO.setEnrollSdate(enrollSdate);
		cScheduleDTO.setPublishDate(publishDate);

		
		cScheduleDTO.setUserId(userId);
		return services.addCourseMaster(cMasterDTO, cScheduleDTO, file, video, banner);

	}


	@PostMapping(value = "/updateCourse")
	public String updateCourseMaster(@RequestPart("categoryId") String categoryId,
			@RequestPart("courseId") String courseId, @RequestPart("userId") String userId,
			@RequestPart("courseFee") String courseFee, @RequestPart("courseName") String courseName,
			@RequestPart("courseType") String courseType, @RequestPart("duration") String duration,
			@RequestPart("courseAccessType") String courseAccessType,
			@RequestPart("generalDetails") String generalDetails, @RequestPart("prerequisite") String prerequisite,
			@RequestPart("objective") String objective, @RequestPart("inst_profile") String inst_profile,
			@RequestPart("fee_discount") String fee_discount, @RequestPart("commencementDate") String commencementDate,
			@RequestPart("enrollEdate") String enrollEdate, @RequestPart("enrollSdate") String enrollSdate,
			@RequestPart("isScormCompliant") String isScormCompliant, @RequestPart("publishDate") String publishDate,
			@RequestPart(required = false) MultipartFile file, @RequestPart(required = false) MultipartFile video,
			@RequestPart(required = false) MultipartFile banner) {

		CourseMasterDTO cMasterDTO = new CourseMasterDTO();
		CourseScheduleDTO cScheduleDTO = new CourseScheduleDTO();
		cMasterDTO.setCategoryId(Integer.parseInt(categoryId));
		cMasterDTO.setCourse_Fee(Integer.parseInt(courseFee));
		cMasterDTO.setCourse_Type(courseType);
		cMasterDTO.setCourseName(courseName);
		cMasterDTO.setDuration(Integer.parseInt(duration));

		cMasterDTO.setCourse_access_type(courseAccessType);
		cMasterDTO.setGeneralDetails(generalDetails);
		cMasterDTO.setIsScormCompliant(Integer.parseInt(isScormCompliant));
		cMasterDTO.setPrerequisite(prerequisite);
		cMasterDTO.setObjective(objective);
		cMasterDTO.setInst_profile(inst_profile);
		cMasterDTO.setFee_discount(Integer.parseInt(fee_discount));

		cMasterDTO.setCourseId(Integer.parseInt(courseId));
		cMasterDTO.setUserId(userId);
		cScheduleDTO.setCommencementDate(commencementDate);
		cScheduleDTO.setEnrollEdate(enrollEdate);
		cScheduleDTO.setEnrollSdate(enrollSdate);
		cScheduleDTO.setPublishDate(publishDate);
		cScheduleDTO.setUserId(userId);
		return services.updateCourseMaster(cMasterDTO, cScheduleDTO, file, video, banner);
	}


	@PostMapping(value = "/deleteCourse/{courseId}")
	public String deleteCourseMaster(@PathVariable int courseId) {
		return services.deleteCourse(courseId);
	}

	@PostMapping(value = "/addChild")
	public String addChildData(@RequestBody DirDetailsDTO dirDto) {
		System.out.println("input while add subFolder"+ dirDto );
		return services.addChildData(dirDto);
	}


	@PostMapping(value = "/updateChild")
	public String updateChild(@RequestBody DirDetailsDTO dirDto) {
		return services.updateChildData(dirDto);
	}

	@PostMapping(value = "/deleteChild")
	public String deleteChild(@RequestBody DirDetailsDTO dirDto) {
		return services.deleteDirectory(dirDto);
	}

	@GetMapping(value = "/getCourses")
	public List<UpdatedCourseMasterInterface> getAllCourses() {
		return services.getAllCourses();
	}


	@GetMapping(value = "/getCoursesByuserId/{userId}")
	public List<UpdatedCourseMasterInterface> getCoursesByuserId(@PathVariable String userId) {
		return services.getCoursesByuserId(userId);
	}


	@GetMapping(value = "/getPublishCourses")
	public List<UpdatedCourseMasterInterface> getAllPublishCourses() {
		return services.getAllPublishCourses();
	}

	@GetMapping(value = "/activeCourses")
	public List<UpdatedCourseMasterDTO> getActiveCourses() {
		return services.getActiveCourses();
	}

	@GetMapping(value = "/getCourses/{courseId}")
	public UpdatedCourseMasterInterface getCourseById(@PathVariable int courseId) {
		return services.getCourseById(courseId);
	}

	@PostMapping(value = "/publishCourse/{courseId}")
	public String publishCourse(@PathVariable int courseId) {
		return services.postPublishCourseStatus(courseId);
	}

	@GetMapping(value = "/getCourseMetadata/{courseId}")
	public String getInstructorMetadata(@PathVariable int courseId) {
		return services.getInstructorMetadata(courseId);
	}

	@GetMapping(value = "/getCourseStructure/{courseId}")
	public String getInstructorStructure(@PathVariable int courseId) {
		return services.getInstructorStructure(courseId);
	}

	@GetMapping(value = "/getSystemDate")
	public String getSystemDate() {
		return services.getSystemDate();
	}

	@PostMapping(value = "/UnPublishCourse/{courseId}")
	public String courseStatusUnpublish(@PathVariable int courseId) {
		return services.courseStatusUnPublish(courseId);
	}

	@PostMapping(value = "/CourseDisableStatus/{courseId}")
	public String courseStatusDisable(@PathVariable int courseId) {
		return services.courseStatusDisable(courseId);
	}

}
