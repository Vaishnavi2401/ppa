package com.cdac.coursecatalouge.services;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.coursecatalouge.DTO.CourseInfoDTO;
import com.cdac.coursecatalouge.DTO.LearnerInstructorCouseCountDTO;
import com.cdac.coursecatalouge.DTO.LearnerMasterDTO;
import com.cdac.coursecatalouge.DTO.LearnerMasterLitePojo;
import com.cdac.coursecatalouge.DTO.TenantCourseDetailDTO;
import com.cdac.coursecatalouge.DTO.UserCountDTO;
import com.cdac.coursecatalouge.DTO.UserTenantCourseMapDTO;
import com.cdac.coursecatalouge.DTO.UserTenantCourseMapPKDTO;
import com.cdac.coursecatalouge.feign.AssessmentClient;
import com.cdac.coursecatalouge.feign.UserManagementClient;
import com.cdac.coursecatalouge.models.TenantCourseDetail;
import com.cdac.coursecatalouge.models.TenantCourseDetailPK;
import com.cdac.coursecatalouge.models.UserTenantCourseMap;
import com.cdac.coursecatalouge.models.UserTenantCourseMapPK;
import com.cdac.coursecatalouge.respositories.AllCourseDetails;
import com.cdac.coursecatalouge.respositories.TenantCourseDetailsRepo;
import com.cdac.coursecatalouge.respositories.UserTenantCourseMapRepository;
//import com.cdac.courseorg.repositories.UpdatedCourseMasterInterface;

@Service
public class UserCourseService {

	@Autowired
	AllCourseDetails allCourseDetailsRepository;
	@Autowired
	UserTenantCourseMapRepository userTenantCourseMapRepository;

	@Autowired
	TenantCourseDetailsRepo tenantCourseDetailRepo;

	@Autowired
	UserManagementClient userManagementClient;

	@Autowired
	AssessmentClient assessmentClient;

	@Autowired
	CourseListService courseList;

	/*
	 * public UserDetail create(String userId, int courseId, int batchId, int
	 * tenantId) { UserDetail ud = new UserDetail(); UserDetailPK udpk = new
	 * UserDetailPK(); udpk.setCourseId(courseId); udpk.setUserId(userId);
	 * udpk.setRoleId(4); ud.setId(udpk);
	 * 
	 * LearnerMasterDTO learnerDetails =
	 * userManagementClient.getUserDetails(userId); Optional<CourseSchedule> cs =
	 * courseSchedule.findById(courseId); if (cs.isPresent()) {
	 * ud.setStartDate(cs.get().getEnrollSdate());
	 * ud.setEndDate(cs.get().getEnrollEdate()); ud.getId().setRoleId(4); //
	 * System.out.println(learnerDetails.getEmail());
	 * ud.setOriginalEmailid(learnerDetails.getEmail());
	 * ud.setSuspendAll(Integer.toString(1)); }
	 * 
	 * userCourseRepository.save(ud);
	 * 
	 * RegisterUserAssessmentDTO registerAssessment = new
	 * RegisterUserAssessmentDTO(); registerAssessment.setApplicationID(tenantId);
	 * registerAssessment.setCourseID(Integer.toString(courseId));
	 * registerAssessment.setUserKey(userId);
	 * 
	 * assessmentClient.registerUserinAssessment(registerAssessment);
	 * 
	 * return ud; }
	 */
	public Optional<UserTenantCourseMap> getEnrollmentStatus(String userId, int courseId, int roleId) {
		return userTenantCourseMapRepository.findByIdUserIdAndIdCourseIdAndIdRoleId(userId, courseId, roleId);
	}

	public Integer getUserOrInstructorForCourseStatus(String userId, int courseId) {
		return userTenantCourseMapRepository.findByIdUserIdAndCourseIdUserOrInstructorCourseStatus(userId, courseId);
//		UserTenantCourseMapDTO userTenantCourseMapDTO;
//		UserTenantCourseMapPKDTO userTenantCourseMapPKDTO;
//		List<UserTenantCourseMapDTO> listTenantCourseDTO = new ArrayList<>();
//		for (UserTenantCourseMap c : courseList) {
//			userTenantCourseMapDTO = new UserTenantCourseMapDTO();
//			userTenantCourseMapPKDTO = new UserTenantCourseMapPKDTO();
//
//			userTenantCourseMapPKDTO.setRoleId(c.getId().getRoleId());
//			userTenantCourseMapDTO.setId(userTenantCourseMapPKDTO);
//
//			listTenantCourseDTO.add(userTenantCourseMapDTO);
//		}
//		return listTenantCourseDTO;
	}

	public List<CourseInfoDTO> getUserEnrolledCourses(String userId) {
		List<CourseInfoDTO> eventList = allCourseDetailsRepository.findCoursesByUserId(userId).stream().map(e -> {
			CourseInfoDTO dto = new CourseInfoDTO();
			dto.setTenantId(e.getTenantId());
			dto.setCatId(e.getCategoryId());
			dto.setCatName(e.getCategory());
			dto.setCourseDescription(e.getCourseDescription());
			dto.setCourseId(e.getCourseId());
			dto.setCourseName(e.getCourseName());
			dto.setCourseImage(e.getImageUrl());
			if (e.getDuration() != null) {
				dto.setDuration(e.getDuration());
			}
			dto.setPublishDate(e.getPublishDate());
			dto.setClosingDate(e.getCourseClosingDate());
			dto.setCourseType(e.getCourseType());
			dto.setFees(e.getCourseFee());
			List<String> authors = userTenantCourseMapRepository.findAuthorsByCourseId(e.getCourseId(),
					e.getTenantId());
			List<LearnerMasterDTO> instructorDetails = null;
			if (authors.size() > 0) {

				instructorDetails = userManagementClient
						.getInstructorDetails(authors.toArray(new String[authors.size()]));
			}

			dto.setInstructor(instructorDetails);
			Integer userCount = userTenantCourseMapRepository.findUserCount(e.getCourseId(), e.getTenantId());
			dto.setUserCount(userCount);
			return dto;
		}).collect(Collectors.toList());

		return eventList;
	}

	/*
	 * public int getEnrolledUserCount(int courseId, int roleId) {
	 * 
	 * return userCourseRepository.findByIdCourseIdAndIdRoleId(courseId,
	 * roleId).size(); }
	 */

	public LearnerInstructorCouseCountDTO getLearnerInstructorCourseCount() {

		LearnerInstructorCouseCountDTO learnerDTO = new LearnerInstructorCouseCountDTO();

		// Course Count

		long courseCount = tenantCourseDetailRepo.count();
		int instructorCount = userTenantCourseMapRepository.getDistinctIdUserIdByIdRoleId(2).size(); // 2- instructor
		int learnerCount = userTenantCourseMapRepository.getDistinctIdUserIdByIdRoleId(1).size(); // 1- learner

		learnerDTO.setCourseCount((int) courseCount);
		learnerDTO.setInstructorCount(instructorCount);
		learnerDTO.setLearnerCount(learnerCount);

		return learnerDTO;

	}

	/*
	 * public boolean getCourseStatusReport(int courseId, String userId) {
	 * 
	 * CourseStatusReportPK coursePk = new CourseStatusReportPK();
	 * 
	 * coursePk.setCourseId(courseId); coursePk.setUserId(userId);
	 * 
	 * Optional<CourseStatusReport> courseStatus =
	 * courseStatusRepo.findById(coursePk);
	 * 
	 * if (courseStatus.isPresent()) {
	 * 
	 * if (courseStatus.get().getProgress().trim().equals("Completed")) { return
	 * true; }
	 * 
	 * }
	 * 
	 * return false; }
	 */

	public String userCourseEnrollment(UserTenantCourseMap userTenantMap, String cName) throws ParseException {
		userTenantCourseMapRepository.save(userTenantMap);
		
		
		userManagementClient.sendEmailforCourseEnrollStatus(userTenantMap.getId().getUserId(), cName, 4);
		
		return "User Enrolled successfully";
	}

	public String tenantCourseDetailsStore(TenantCourseDetail tenantCourseDetail, String userId) {
		System.out.println(" inside service before storing TenantCourseDetails");
		TenantCourseDetailPK tcpk = new TenantCourseDetailPK();
		UserTenantCourseMap ut = new UserTenantCourseMap();
		UserTenantCourseMapPK utpk = new UserTenantCourseMapPK();
		utpk.setUserId(userId);
		utpk.setRoleId(2);
		utpk.setTenantId(tenantCourseDetail.getId().getTenantId());
		utpk.setCourseId(tenantCourseDetail.getId().getCourseId());
		ut.setId(utpk);
		ut.setTenantCourseDetail(tenantCourseDetail);
		tenantCourseDetailRepo.save(tenantCourseDetail);
		userTenantCourseMapRepository.save(ut);
		return "Course Details Store in Succesfully";
	}

//	//method for approving user as instructor
//	public String tenantCourseMapDetailsStore(int courseId, int tenantId,String userId) {
//		try {
//			
//			UserTenantCourseMap userTenantCourseMap=new UserTenantCourseMap();
//			UserTenantCourseMap ut = new UserTenantCourseMap();
//			UserTenantCourseMapPK utpk = new UserTenantCourseMapPK();
//			utpk.setUserId(userId);
//			utpk.setRoleId(2);
//			utpk.setTenantId(tenantId);
//			utpk.setCourseId(courseId);
//			ut.setId(utpk);
//			Optional<CourseMaster> courseMetaData=courseList.getCourseMetaData(courseId,tenantId);
//			userTenantCourseMap.setCourseStartDate(courseMetaData.get().getCourseSchedule().getCommencementDate());
//			userTenantCourseMap.setCourseEndDate(courseMetaData.get().getCourseSchedule().getCourseClosingDate());
//			userTenantCourseMap.setId(utpk);
//			userTenantCourseMapRepository.save(ut);
//			return "success";
//		}catch (Exception e) {
//			e.printStackTrace();
//			return "error";
//		}
//				
//	}

	public String updateTenantCourseDetails(TenantCourseDetail tenantCourseDetail) {
		tenantCourseDetailRepo.save(tenantCourseDetail);
		return "course details updated successfully";
	}

	public String deleteTenantCourseDetails(int courseId) {
		tenantCourseDetailRepo.deleteCourseByCourseId(courseId);
		return "course details deleted successfully";
	}

	public List<UserTenantCourseMapDTO> getCoursesEnrolledByUser(String userId, int roleId) {
		List<UserTenantCourseMap> courseList = userTenantCourseMapRepository.findCoursesByuserIdAndRoleId(userId,
				roleId);
		UserTenantCourseMapDTO userTenantCourseMapDTO;
		UserTenantCourseMapPKDTO userTenantCourseMapPKDTO;
		TenantCourseDetailDTO tenantCourseDetailDTO;
		List<UserTenantCourseMapDTO> listTenantCourseDTO = new ArrayList<>();
//		List<TenantCourseDetailDTO> listCourseDetailDTO = new ArrayList<>();
		for (UserTenantCourseMap c : courseList) {
//			for(TenantCourseDetail t : courseList) {
			userTenantCourseMapDTO = new UserTenantCourseMapDTO();
			tenantCourseDetailDTO = new TenantCourseDetailDTO();
			userTenantCourseMapPKDTO = new UserTenantCourseMapPKDTO();

			userTenantCourseMapDTO.setCourseEndDate(c.getCourseEndDate());
			userTenantCourseMapDTO.setCourseStartDate(c.getCourseStartDate());
			

			TenantCourseDetail tcd = c.getTenantCourseDetail();
			tenantCourseDetailDTO = new TenantCourseDetailDTO();

			tenantCourseDetailDTO.setCategoryId(tcd.getCategoryId());
			tenantCourseDetailDTO.setCategory(tcd.getCategory());
			tenantCourseDetailDTO.setCourseName(tcd.getCourseName());
			tenantCourseDetailDTO.setCourse_Fee(tcd.getCourseFee());
			tenantCourseDetailDTO.setDuration(tcd.getDuration());
			tenantCourseDetailDTO.setCourse_Type(tcd.getCourseType());
			tenantCourseDetailDTO.setCommencementDate(tcd.getCommencementDate());
			tenantCourseDetailDTO.setCourseClosingDate(tcd.getCourseClosingDate());
			tenantCourseDetailDTO.setEnrollSdate(tcd.getEnrollSdate());
			tenantCourseDetailDTO.setEnrollEdate(tcd.getEnrollEdate());
			tenantCourseDetailDTO.setPublishDate(tcd.getPublishDate());
			tenantCourseDetailDTO.setImageUrl(tcd.getImageUrl());
			tenantCourseDetailDTO.setStatus(tcd.getStatus());

			tenantCourseDetailDTO.setCourseDescription(tcd.getCourseDescription());

			userTenantCourseMapDTO.setCourseDetails(tenantCourseDetailDTO);

			userTenantCourseMapPKDTO.setCourseId(c.getId().getCourseId());
			userTenantCourseMapPKDTO.setUserId(c.getId().getUserId());
			userTenantCourseMapPKDTO.setTenantId(c.getId().getTenantId());
			userTenantCourseMapPKDTO.setRoleId(c.getId().getRoleId());
			userTenantCourseMapDTO.setId(userTenantCourseMapPKDTO);

			List<String> authors = userTenantCourseMapRepository.findAuthorsByCourseId(c.getId().getCourseId(),
					c.getId().getTenantId());
			List<LearnerMasterDTO> instructorDetails = null;
			if (authors.size() > 0) {
				instructorDetails = userManagementClient
						.getInstructorDetails(authors.toArray(new String[authors.size()]));
			}

			userTenantCourseMapDTO.setInstructor(instructorDetails);
			Integer userCount = userTenantCourseMapRepository.findUserCount(c.getId().getCourseId(),
					c.getId().getTenantId());
			tenantCourseDetailDTO.setUserCount(userCount);

			listTenantCourseDTO.add(userTenantCourseMapDTO);
		}
		return listTenantCourseDTO;
	}

	public UserTenantCourseMapDTO getCoursesDeatilsEnrolledByUser(String userId, int courseId) {
		List<UserTenantCourseMap> courseList = userTenantCourseMapRepository.findCoursesByuserIdAndCourseId(userId,
				courseId);
		UserTenantCourseMapDTO userTenantCourseMapDTO;
		UserTenantCourseMapPKDTO userTenantCourseMapPKDTO;
		TenantCourseDetailDTO tenantCourseDetailDTO;
		// List<UserTenantCourseMapDTO> listTenantCourseDTO = new ArrayList<>();
//		List<TenantCourseDetailDTO> listCourseDetailDTO = new ArrayList<>();
		// for (UserTenantCourseMap c : courseList) {
//			for(TenantCourseDetail t : courseList) {
		userTenantCourseMapDTO = new UserTenantCourseMapDTO();
		tenantCourseDetailDTO = new TenantCourseDetailDTO();
		userTenantCourseMapPKDTO = new UserTenantCourseMapPKDTO();

		userTenantCourseMapDTO.setCourseEndDate(courseList.get(0).getCourseEndDate());
		userTenantCourseMapDTO.setCourseStartDate(courseList.get(0).getCourseStartDate());
		TenantCourseDetail tcd = courseList.get(0).getTenantCourseDetail();
		tenantCourseDetailDTO = new TenantCourseDetailDTO();

		tenantCourseDetailDTO.setCategoryId(tcd.getCategoryId());
		tenantCourseDetailDTO.setCategory(tcd.getCategory());
		tenantCourseDetailDTO.setCourseName(tcd.getCourseName());
		tenantCourseDetailDTO.setCourse_Fee(tcd.getCourseFee());
		tenantCourseDetailDTO.setDuration(tcd.getDuration());
		tenantCourseDetailDTO.setCourse_Type(tcd.getCourseType());
		tenantCourseDetailDTO.setCommencementDate(tcd.getCommencementDate());
		tenantCourseDetailDTO.setCourseClosingDate(tcd.getCourseClosingDate());
		tenantCourseDetailDTO.setEnrollSdate(tcd.getEnrollSdate());
		tenantCourseDetailDTO.setEnrollEdate(tcd.getEnrollEdate());
		tenantCourseDetailDTO.setPublishDate(tcd.getPublishDate());
		tenantCourseDetailDTO.setImageUrl(tcd.getImageUrl());
		tenantCourseDetailDTO.setStatus(tcd.getStatus());

		tenantCourseDetailDTO.setCourseDescription(tcd.getCourseDescription());

		userTenantCourseMapDTO.setCourseDetails(tenantCourseDetailDTO);

		userTenantCourseMapPKDTO.setCourseId(courseList.get(0).getId().getCourseId());
		userTenantCourseMapPKDTO.setUserId(courseList.get(0).getId().getUserId());
		userTenantCourseMapPKDTO.setTenantId(courseList.get(0).getId().getTenantId());
		userTenantCourseMapPKDTO.setRoleId(courseList.get(0).getId().getRoleId());
		userTenantCourseMapDTO.setId(userTenantCourseMapPKDTO);

		List<String> authors = userTenantCourseMapRepository.findAuthorsByCourseId(
				courseList.get(0).getId().getCourseId(), courseList.get(0).getId().getTenantId());
		List<LearnerMasterDTO> instructorDetails = null;
		if (authors.size() > 0) {
			instructorDetails = userManagementClient.getInstructorDetails(authors.toArray(new String[authors.size()]));
		}

		userTenantCourseMapDTO.setInstructor(instructorDetails);
		Integer userCount = userTenantCourseMapRepository.findUserCount(courseList.get(0).getId().getCourseId(),
				courseList.get(0).getId().getTenantId());
		tenantCourseDetailDTO.setUserCount(userCount);

		// listTenantCourseDTO.add(userTenantCourseMapDTO);
		// }
		return userTenantCourseMapDTO;
	}

	public UserCountDTO getUserCount(int courseId, int tenantId) {
		UserCountDTO uc = new UserCountDTO();
		int userCount = userTenantCourseMapRepository.findUserCount(courseId, tenantId);
		uc.setUserCount(userCount);
		return uc;
	}

//	public List<UserTenantCourseMap> getCoursesEnrolledByUser(String userId) {
//		return userTenantCourseMapRepository.findCoursesByuserId(userId);
//	}

	public List<LearnerMasterLitePojo> getEnrolledUserList(int courseId, int tenantId, int role) {

		// int role = 1; // roleid 1 for learner
		List<UserTenantCourseMap> learnerList = userTenantCourseMapRepository.findByIdRoleIdAndIdCourseId(role,
				courseId);
		List<LearnerMasterLitePojo> learnerdetailslist = new ArrayList<LearnerMasterLitePojo>();

		for (UserTenantCourseMap c : learnerList) {
			LearnerMasterLitePojo learnerDetails = userManagementClient.getUserDetails(c.getId().getUserId());
			learnerdetailslist.add(learnerDetails);

		}

		return learnerdetailslist;
	}

	public String getCourseAuthorStatus(int courseId, String userId) {

		int userrole = userTenantCourseMapRepository.findByIdUserIdAndCourseIdUserOrInstructorCourseStatus(userId,
				courseId);

		if (userrole == 2) {
			return "true";
		} else {
			return "false";
		}

	}

	public List<LearnerMasterLitePojo> getunapprovedCourseEnrollRequest(int courseId) {

		List<LearnerMasterLitePojo> learnerdetailslist = null;
		try {
			learnerdetailslist = new ArrayList<LearnerMasterLitePojo>();
			List<UserTenantCourseMap> usercourmap = userTenantCourseMapRepository.findByIdRoleIdAndIdCourseId(4,
					courseId); // role id 4 for fetching unapproved users list
			for (UserTenantCourseMap c : usercourmap) {
				LearnerMasterLitePojo learnerDetails = userManagementClient.getUserDetails(c.getId().getUserId());
				learnerdetailslist.add(learnerDetails);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return learnerdetailslist;

	}

	@Transactional
	public String approveUserCourseEnrollRequest(String userId, int courseId) {

		try {

			List<UserTenantCourseMap> usercourmap = userTenantCourseMapRepository.findCoursesByuserIdAndCourseId(userId,
					courseId);
			if ((usercourmap.size() != 0)) {
				Timestamp currtime= Timestamp.from(Instant.now());  
				userTenantCourseMapRepository.updateUserCourseEnrollStatus(1, userId, courseId,currtime); //role id 1 for learner role approval
				userManagementClient.sendEmailforCourseEnrollStatus(userId, usercourmap.get(0).getTenantCourseDetail().getCourseName(), 1);
			}

			return "success";

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return "error";
		}

	}
	
	@Transactional
	public String rejectUserCourseEnrollRequest(String userId, int courseId) {

		try {
			System.out.println("inside Service");
			List<UserTenantCourseMap> usercourmap = userTenantCourseMapRepository.findCoursesByuserIdAndCourseId(userId,
					courseId);
			if ((usercourmap.size() != 0)) {
				Timestamp currtime= Timestamp.from(Instant.now());  
				userTenantCourseMapRepository.updateUserCourseEnrollStatus(5, userId, courseId,currtime); //role id 5 for request rejection
				userManagementClient.sendEmailforCourseEnrollStatus(userId, usercourmap.get(0).getTenantCourseDetail().getCourseName(), 5);
			}

			return "success";

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return "error";
		}

	}

}
