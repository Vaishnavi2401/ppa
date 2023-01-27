package com.cdac.courseorg.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cdac.courseorg.dtos.ContentDetailDTO;
import com.cdac.courseorg.dtos.CourseCategoryDTO;
import com.cdac.courseorg.dtos.CourseMasterDTO;
import com.cdac.courseorg.dtos.CourseScheduleDTO;
import com.cdac.courseorg.dtos.DirDetailsDTO;
import com.cdac.courseorg.dtos.UpdatedCourseMasterDTO;
import com.cdac.courseorg.models.ContentDetail;
import com.cdac.courseorg.models.CourseCategory;
import com.cdac.courseorg.repositories.UpdatedCourseMasterInterface;

public interface CourseOrganizerServices {
	// course category services
	String addCourseCategory(CourseCategoryDTO categoryDto);

	List<CourseCategory> getAllCourseCategories();

	String updateCategory(CourseCategoryDTO categoryDto);

	String deleteCategory(int categoryId);

	// course master services
	String addCourseMaster(CourseMasterDTO cMasterDto, CourseScheduleDTO cScheduleDto, MultipartFile file,
			MultipartFile video, MultipartFile banner);

	List<UpdatedCourseMasterInterface> getAllCourses();

	UpdatedCourseMasterInterface getCourseById(int id);

	List<UpdatedCourseMasterDTO> getActiveCourses();

	String updateCourseMaster(CourseMasterDTO cMasterDto, CourseScheduleDTO cScheduleDto, MultipartFile file,
			MultipartFile video, MultipartFile banner);

	String deleteCourse(int courseId);

	String addChildData(DirDetailsDTO dirDTo);

	String updateChildData(DirDetailsDTO dirDTo);

	String deleteDirectory(DirDetailsDTO dirDTo);

	String postPublishCourseStatus(int courseId);

	List<UpdatedCourseMasterInterface> getAllPublishCourses();

	// course content services
	String addContent(List<ContentDetailDTO> contentDto);

	String updateContent(ContentDetailDTO contentDto);

	String deleteContent(ContentDetailDTO contentDto);

	boolean contentStatusCheck(int p_content_id);

	String getInstructorMetadata(int courseId);

	String getInstructorStructure(int courseId);

	String getSystemDate();

	String courseStatusUnPublish(int courseId);

	String courseStatusDisable(int courseId);

	List<UpdatedCourseMasterInterface> getCoursesByuserId(String userId);

}
