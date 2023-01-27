package com.cdac.coursecatalouge.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.coursecatalouge.DTO.CourseInfoDTO;
import com.cdac.coursecatalouge.DTO.LearnerMasterDTO;
import com.cdac.coursecatalouge.services.CourseListService;
import com.cdac.coursecatalouge.services.UserCourseService;

import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/courses/")
@CrossOrigin("*")
public class CourseListController {
	
	@Autowired
	CourseListService courseList;
	
	@Autowired
	UserCourseService userCourseService;

	@ApiOperation(value = "Returns  Courses list ")
	
	@GetMapping(value="/courses")
	public ResponseEntity<List<CourseInfoDTO>> getAllCourses() {		
		return new ResponseEntity<>(courseList.getCourseList(), HttpStatus.OK);		

	}
	
	
	@GetMapping("/getCourseByCourseIds")
	public ResponseEntity<List<CourseInfoDTO>> getCourseMetaDataByCourseId(@RequestParam List<Integer> courseIds,@RequestParam int tenantId) {
		
		return new ResponseEntity<>(courseList.getCourseListByCourseId(courseIds,tenantId), HttpStatus.OK);	
		
		
	
	}
	
	@GetMapping("/getCourseByCourseId")
	public CourseInfoDTO getCourseDetailsByCourseId(@RequestParam int courseId, @RequestParam int tenantId) {
		System.out.println("courseId-------"+courseId+"----------tenant Id "+ tenantId);
		return courseList.getCourseByCourseId(courseId, tenantId).get(0);	
		
		
	}
	
	
	@GetMapping("/getInstructorByCourseId")
	public ResponseEntity<List<LearnerMasterDTO>> getInstructorByCourseId(@RequestParam int courseId,@RequestParam int tenantId) {
		
		return new ResponseEntity<>(courseList.getInstructoByCourseId(courseId,tenantId), HttpStatus.OK);		
	
	}
	
}
