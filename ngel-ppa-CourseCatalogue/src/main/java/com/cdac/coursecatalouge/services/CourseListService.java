package com.cdac.coursecatalouge.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.coursecatalouge.DTO.CourseInfoDTO;
import com.cdac.coursecatalouge.DTO.LearnerInstructorCouseCountDTO;
import com.cdac.coursecatalouge.DTO.LearnerMasterDTO;
import com.cdac.coursecatalouge.feign.MeghSikshakClient;
import com.cdac.coursecatalouge.feign.UserManagementClient;
import com.cdac.coursecatalouge.respositories.AllCourseDetails;

import com.cdac.coursecatalouge.respositories.TenantCourseDetailsRepo;
import com.cdac.coursecatalouge.respositories.UserTenantCourseMapRepository;

@Service
public class CourseListService {
	
	@Autowired
	AllCourseDetails allCourseDetailsRepository;
	@Autowired
	UserTenantCourseMapRepository userTenantCourseMapRepository;

	@Autowired
	UserManagementClient userManagmentClient;

	public List<CourseInfoDTO> getCourseList() {
		List<CourseInfoDTO> eventList = allCourseDetailsRepository.findCourses().stream().map(e -> {
			CourseInfoDTO dto = new CourseInfoDTO();
			dto.setTenantId(e.getTenantId());
			dto.setCatId(e.getCategoryId());
			dto.setCatName(e.getCategory());
			dto.setCourseDescription(e.getCourseDescription());
			dto.setCourseId(e.getCourseId());
			dto.setCourseName(e.getCourseName());
			if (e.getDuration() != null) {
				dto.setDuration(e.getDuration());
			}
			dto.setCourseImage(e.getImageUrl());
			dto.setPublishDate(e.getPublishDate());
			dto.setClosingDate(e.getCourseClosingDate());
			dto.setCourseSDate(e.getCourseSDate());
			dto.setCourseEDate(e.getCourseEDate());
			dto.setCourseType(e.getCourseType());
			dto.setFees(e.getCourseFee());
			dto.setStatus(e.getStatus());
			List<String> authors = userTenantCourseMapRepository.findAuthorsByCourseId(e.getCourseId(),
					e.getTenantId());
			List<LearnerMasterDTO> instructorDetails = null;

			if (authors.size() > 0) {

				instructorDetails = userManagmentClient
						.getInstructorDetails(authors.toArray(new String[authors.size()]));
			}

			dto.setInstructor(instructorDetails);
			Integer userCount = userTenantCourseMapRepository.findUserCount(e.getCourseId(), e.getTenantId());
			dto.setUserCount(userCount);
			return dto;
		}).collect(Collectors.toList());

		return eventList;
	}

	public List<CourseInfoDTO> getCourseListByCourseId(List<Integer> courseIds, int tenantId) {

		List<CourseInfoDTO> eventList = allCourseDetailsRepository.findCoursesByCourseId(courseIds, tenantId).stream()
				.map(e -> {
					CourseInfoDTO dto = new CourseInfoDTO();
					dto.setTenantId(e.getTenantId());
					dto.setCatId(e.getCategoryId());
					dto.setCatName(e.getCategory());
					dto.setCourseDescription(e.getCourseDescription());
					dto.setCourseId(e.getCourseId());
					dto.setCourseName(e.getCourseName());
					if (e.getDuration() != null) {
						dto.setDuration(e.getDuration());
					}
					dto.setCourseImage(e.getImageUrl());
					dto.setPublishDate(e.getPublishDate());
					dto.setClosingDate(e.getCourseClosingDate());
					dto.setCourseType(e.getCourseType());
					dto.setFees(e.getCourseFee());
					List<String> authors = userTenantCourseMapRepository.findAuthorsByCourseId(e.getCourseId(),
							e.getTenantId());
					List<LearnerMasterDTO> instructorDetails = null;
					if (authors.size() > 0) {

						instructorDetails = userManagmentClient
								.getInstructorDetails(authors.toArray(new String[authors.size()]));
					}

					dto.setInstructor(instructorDetails);
					Integer userCount = userTenantCourseMapRepository.findUserCount(e.getCourseId(), e.getTenantId());
					dto.setUserCount(userCount);
					return dto;
				}).collect(Collectors.toList());

		return eventList;
	}
	
	public List<CourseInfoDTO> getCourseByCourseId(int courseId, int tenantId) {
		System.out.println("courseId1-------"+courseId+"----------tenant Id1 "+ tenantId);
		List<CourseInfoDTO> eventList = allCourseDetailsRepository.findCourseByCourseId(courseId, tenantId).stream()
				.map(e -> {
					CourseInfoDTO dto = new CourseInfoDTO();
					dto.setTenantId(e.getTenantId());
					dto.setCatId(e.getCategoryId());
					dto.setCatName(e.getCategory());
					dto.setCourseDescription(e.getCourseDescription());
					dto.setCourseId(e.getCourseId());
					dto.setCourseName(e.getCourseName());
//					System.out.println("CoursestartDate---- "+ e.getCourseSDate());
					//System.out.println("CourseendDate---- "+ e.getCourseClosingDate());
					dto.setCourseSDate(e.getCourseSDate());
					dto.setCourseEDate(e.getCourseClosingDate());
					if (e.getDuration() != null) {
						dto.setDuration(e.getDuration());
					}
					dto.setCourseImage(e.getImageUrl());
					dto.setPublishDate(e.getPublishDate());
					dto.setClosingDate(e.getCourseClosingDate());
					dto.setCourseType(e.getCourseType());
					dto.setFees(e.getCourseFee());
					List<String> authors = userTenantCourseMapRepository.findAuthorsByCourseId(e.getCourseId(),
							e.getTenantId());
					List<LearnerMasterDTO> instructorDetails = null;
					if (authors.size() > 0) {

						instructorDetails = userManagmentClient
								.getInstructorDetails(authors.toArray(new String[authors.size()]));
					}

					dto.setInstructor(instructorDetails);
					Integer userCount = userTenantCourseMapRepository.findUserCount(e.getCourseId(), e.getTenantId());
					dto.setUserCount(userCount);
					return dto;
				}).collect(Collectors.toList());
		System.out.println("event List------"+eventList.size());
		return eventList;
	}
	

	public List<LearnerMasterDTO> getInstructoByCourseId(int courseId, int tenantId) {

		List<String> instructor = userTenantCourseMapRepository.findAuthorsByCourseId(courseId, tenantId);
		List<LearnerMasterDTO> instructorDetails = null;

		System.out.println("--" + instructor.get(0));
		if (instructor.size() > 0) {

			instructorDetails = userManagmentClient
					.getInstructorDetails(instructor.toArray(new String[instructor.size()]));
		}

		return instructorDetails;

	}



}
