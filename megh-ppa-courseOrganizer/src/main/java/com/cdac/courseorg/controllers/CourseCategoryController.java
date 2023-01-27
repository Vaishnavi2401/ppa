package com.cdac.courseorg.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.courseorg.dtos.CourseCategoryDTO;
import com.cdac.courseorg.models.CourseCategory;
import com.cdac.courseorg.services.CourseOrganizerServices;

@CrossOrigin("*")
@RequestMapping(value = "/courseOrganizer")
@RestController
public class CourseCategoryController {

	@Autowired
	CourseOrganizerServices services;
	
	
	@PostMapping(value="/addCategory")
	public String addCategory(@RequestBody CourseCategoryDTO categoryDto) {
		return services.addCourseCategory(categoryDto);
	}
	
	@GetMapping(value="/getAllCategories")
	public List<CourseCategory> getAllCourseCategories(){
		return services.getAllCourseCategories();
	}
	
	@PutMapping(value="/updateCategory")
	public String updateCategory(@RequestBody CourseCategoryDTO categoryDto) {
		return services.updateCategory(categoryDto);
	}
	
	@DeleteMapping(value="/deleteCategory/{catId}")
	public String deleteCategory(@PathVariable int catId) {
		return services.deleteCategory(catId);
	}
}
