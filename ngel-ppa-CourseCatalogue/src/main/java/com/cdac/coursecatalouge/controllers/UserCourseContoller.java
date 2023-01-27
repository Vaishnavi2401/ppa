package com.cdac.coursecatalouge.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.coursecatalouge.DTO.CheckUserStatusDTO;
import com.cdac.coursecatalouge.DTO.CourseInfoDTO;
import com.cdac.coursecatalouge.DTO.LearnerInstructorCouseCountDTO;
import com.cdac.coursecatalouge.DTO.LearnerMasterDTO;
import com.cdac.coursecatalouge.DTO.LearnerMasterLitePojo;
import com.cdac.coursecatalouge.DTO.UserTenantCourseMapPojo;
import com.cdac.coursecatalouge.models.TenantCourseDetail;
import com.cdac.coursecatalouge.models.UserRole;
import com.cdac.coursecatalouge.models.UserTenantCourseMap;
import com.cdac.coursecatalouge.models.UserTenantCourseMapPK;
import com.cdac.coursecatalouge.respositories.TenantCourseDetailsRepo;
import com.cdac.coursecatalouge.services.UserCourseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
@Api(value = "CourseCatalouge REST Endpoint", description = "Shows the Courses info")
public class UserCourseContoller {

	private static final String String = null;

	@Autowired
	UserCourseService userCourseEnrollemet;

	@Autowired
	CourseListController courseListController;

	@Autowired
	TenantCourseDetailsRepo tenantCourseDetailsRepo;

	/*
	 * @ApiOperation(value = "User Enrollent For Course")
	 * 
	 * @PostMapping("/enrollToCourse/{userId}/{courseId}/{batchId}") public
	 * ResponseEntity<UserDetail> userEnrollemntToCourse(@PathVariable("userId")
	 * String userId,
	 * 
	 * @PathVariable("courseId") int courseId, @PathVariable("batchId") int batchId,
	 * 
	 * @RequestParam(value = "tenant_id", required = false) int tenantId) {
	 * UserDetail ud = userCourseEnrollemet.create(userId, courseId, batchId,
	 * tenantId); return new ResponseEntity<UserDetail>(ud, HttpStatus.CREATED);
	 * 
	 * }
	 */

	@ApiOperation(value = "Returns  UserEnrollement Status for Course ")
	@GetMapping("/CheckUserEnrollementStatus/{courseId}/{userId}/{roleId}")
	public CheckUserStatusDTO getEnrollementStatus(@PathVariable("courseId") int courseId,
			@PathVariable("userId") String userId) {

		Optional<UserTenantCourseMap> ud = userCourseEnrollemet.getEnrollmentStatus(userId, courseId, 1);
//		Integer rId = userCourseEnrollemet.getUserOrInstructorForCourseStatus(userId, courseId);
		CheckUserStatusDTO checkUserStatus = new CheckUserStatusDTO();
		// by default setting to false
		checkUserStatus.setCertificateGenerated(false);
		checkUserStatus.setCourseEnrolled(false);

		if (ud.isPresent()) {
			checkUserStatus.setCourseEnrolled(true);
		}
//		if (rId == 1) {
//			checkUserStatus.setInstCourseStatus(rId);
//		} else if (rId == null){
//			checkUserStatus.setInstCourseStatus(4);
//		}

//		if(userCourseEnrollemet.getCourseStatusReport(courseId, userId)) {
//			checkUserStatus.setCertificateGenerated(true);
//
//		}

		return checkUserStatus;
	}

	@ApiOperation(value = "Returns  UserEnrolled Courses")
	@GetMapping("getUserEnrolledCourses/{userId}")
	public List<CourseInfoDTO> getCoursesByUserId(@PathVariable("userId") String userId) {
		List<CourseInfoDTO> list = userCourseEnrollemet.getUserEnrolledCourses(userId);
		return list;
	}

	@ApiOperation(value = "Returns Overall Learner Instructor Course Count ")
	@GetMapping("getLearnerInstructorCourseCount")
	public LearnerInstructorCouseCountDTO getLearnerInstructorCount() {

		return userCourseEnrollemet.getLearnerInstructorCourseCount();
	}

//	@PostMapping(value="/addCategory")
//	public String addCategory(@RequestBody CourseCategoryDTO categoryDto) {
//		return services.addCourseCategory(categoryDto);
//	}
//	
//	
	@ApiOperation(value = "User Enroll With Course Details ")
	@PostMapping("/userEnrolltoCourse")
	public String saveUserEnrollCourseDetails(@RequestBody UserTenantCourseMapPojo userTenantCourseMap) {
		try {
			UserTenantCourseMap utcm = new UserTenantCourseMap();
			UserTenantCourseMapPK utcmpk = new UserTenantCourseMapPK();
			utcmpk.setCourseId(userTenantCourseMap.getCourseId());
			utcmpk.setTenantId(userTenantCourseMap.getTenantId());
			utcmpk.setRoleId(4);// for initial request role id is 4(unapprovedrequest)
			utcmpk.setUserId(userTenantCourseMap.getUserId());
			utcm.setId(utcmpk);

			CourseInfoDTO cidto = courseListController.getCourseDetailsByCourseId(userTenantCourseMap.getCourseId(),
					userTenantCourseMap.getTenantId());
			System.out.println("cidto.getCourseEDate-------"+cidto.getCourseEDate());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date ceparsedDate = dateFormat.parse(cidto.getCourseEDate());
			Timestamp cetimestamp = new Timestamp(ceparsedDate.getTime());
			utcm.setCourseEndDate(cetimestamp);

			Date csparsedDate = dateFormat.parse(cidto.getCourseSDate());
			Timestamp cstimestamp = new Timestamp(csparsedDate.getTime());
			utcm.setCourseStartDate(cstimestamp);

			Timestamp ertimestamp = new Timestamp(System.currentTimeMillis());
			utcm.setEnrollReqDate(ertimestamp);
			
//			TenantCourseDetail tcd = new TenantCourseDetail();
//			tcd.setCourseName(cidto.getCourseName());
//			tcd.setTe
			
//			utcm.setTenantCourseDetail(tcd);

			return userCourseEnrollemet.userCourseEnrollment(utcm, cidto.getCourseName());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@ApiOperation(value = "Store Tenant Course Details ")
	@PostMapping("/addtenantCourseDetails")
	public String saveTenantCourseDetails(@RequestBody TenantCourseDetail tenantCourseDetails,
			@RequestParam String userId) {
		try {
			System.out.println("before storing TenantCourseDetails");
			return userCourseEnrollemet.tenantCourseDetailsStore(tenantCourseDetails, userId);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

//	@PostMapping("/addtenantCourseMapDetails")
//	public String saveTenantCourseMapDetails(@RequestParam int courseId, @RequestParam int tenantId,@RequestParam String userId) {
//		try {
//			return userCourseEnrollemet.tenantCourseMapDetailsStore(courseId,tenantId,userId);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return null;
//	}

	@PostMapping("/updateTenantCourseDetails")
	public String updateTenantCourseDetails(@RequestBody TenantCourseDetail tenantCourseDetails) {
		try {
			return userCourseEnrollemet.updateTenantCourseDetails(tenantCourseDetails);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@PostMapping("/deleteTenantCourseDetails/{courseId}")
	public String deleteTenantCourseDetails(@PathVariable int courseId) {
		try {
			return userCourseEnrollemet.deleteTenantCourseDetails(courseId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@ApiOperation(value = "Get Course Enrolled By User through userid and roleid ")
	@GetMapping("/getCoursesEnrolledByUser/{userId}/{roleId}")
	public ResponseEntity<Object> getCoursesEnrolledByUserIdAndRoleId(@PathVariable("userId") String userId,
			@PathVariable("roleId") int roleId) {
		return ResponseEntity.status(HttpStatus.OK).body(userCourseEnrollemet.getCoursesEnrolledByUser(userId, roleId));
	}

	@ApiOperation(value = "Get Course details through userid and courseid ")
	@GetMapping("/getCoursesDetailsEnrolledByUser/{userId}/{courseId}")
	public ResponseEntity<Object> getCoursesDeatilsEnrolledByUserIdAndcourseId(@PathVariable("userId") String userId,
			@PathVariable("courseId") int courseId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(userCourseEnrollemet.getCoursesDeatilsEnrolledByUser(userId, courseId));
	}

	@ApiOperation(value = "Get Course By Id ")
	@GetMapping("/getCoursesBycourseId/{courseId}")
	public List<TenantCourseDetail> getCourseBycourseId(@PathVariable("courseId") int courseId) {
		List<TenantCourseDetail> list = tenantCourseDetailsRepo.findCoursesBycourseId(courseId);
		return list;
	}

	@ApiOperation(value = "Get User Count Those who enroll the courses")
	@GetMapping("/userCount/{courseId}/{tenantId}")
	public ResponseEntity<Object> getUserCounts(@PathVariable("courseId") int courseId,
			@PathVariable("tenantId") int tenantId) {
		return ResponseEntity.status(HttpStatus.OK).body(userCourseEnrollemet.getUserCount(courseId, tenantId));
	}
	
	@ApiOperation(value = "Get RoleId by CourseId and UserId")
	@GetMapping("/getRoleIdbyCourseIdandUserId/{courseId}/{userId}")
	public int getRoleIdbyCourseIdandUserId(@PathVariable("courseId") int courseId, @PathVariable("userId") String userId) {
		return tenantCourseDetailsRepo.findbycourseIdandUserId(courseId, userId);
	}
	

//	@ApiOperation(value = "Update User Tenant Course Status ")
//	@GetMapping("/updateTenantCourseStatus/{courseId}")
//	public ResponseEntity<Object> updateTenantCourseStatusByCourseId(@RequestParam("courseId") String courseId) {
//		return ResponseEntity.status(HttpStatus.OK).body(userCourseEnrollemet.getCoursesEnrolledByUser(courseId));
//	}

//	@ApiOperation(value = "Check User or Instructor for this couse")
//	@GetMapping("/CheckUserOrInstructorForCourseStatus/{userId}/{courseId}")
//	public List<UserTenantCourseMapDTO> getUserOrInstructorForCourseStatus(@PathVariable("userId") String userId,
//			@PathVariable("courseId") int courseId) {
//
//		List<UserTenantCourseMapDTO> ud = userCourseEnrollemet.getUserOrInstructorForCourseStatus(userId, courseId);
////		System.out.println(ud.id);
//		return ud;
//	}

	@ApiOperation(value = "Get learners list enrolled to the course")
	@GetMapping("/getCourseEnrolledLearners/{courseId}/{tenantId}")
	public ResponseEntity<List<LearnerMasterLitePojo>> getLearnersByCourseId(@PathVariable int courseId,
			@PathVariable int tenantId) {
		int role = 1;// learner role 1
		return new ResponseEntity<>(userCourseEnrollemet.getEnrolledUserList(courseId, tenantId, role), HttpStatus.OK);

	}

	@ApiOperation(value = "Get Instructor list for the course")
	@GetMapping("/getCourseInstructors/{courseId}/{tenantId}")
	public ResponseEntity<List<LearnerMasterLitePojo>> getInstructorsByCourseId(@PathVariable int courseId,
			@PathVariable int tenantId) {
		int role = 2;// instructor role 2
		return new ResponseEntity<>(userCourseEnrollemet.getEnrolledUserList(courseId, tenantId, role), HttpStatus.OK);

	}

	@GetMapping("/checkCourseAuthorStatus/{userId}/{courseId}")
	public String checkCourseAuthorStatus(@PathVariable("userId") String userId,
			@PathVariable("courseId") int courseId) {
		try {
			return userCourseEnrollemet.getCourseAuthorStatus(courseId, userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@GetMapping("/getUnapprovedCourseEnrollRequest/{courseId}")
	public List<LearnerMasterLitePojo> getUnapprovedCourseEnrollRequests(@PathVariable("courseId") int courseId) {
		try {
			return userCourseEnrollemet.getunapprovedCourseEnrollRequest(courseId);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@ApiOperation(value = "Approve Course enrollment request by admin ")
	@PostMapping("/approveCourseEnrollRequestByAdmin")
	public String approveCourseEnrollRequestByAdmin(@RequestParam String userId, @RequestParam int courseId) {
		return userCourseEnrollemet.approveUserCourseEnrollRequest(userId, courseId);
	}

	@ApiOperation(value = "Reject Course enrollment request by admin ")
	@PostMapping("/rejectCourseEnrollRequestByAdmin")
	public String rejectCourseEnrollRequestByAdmin(@RequestParam String userId, @RequestParam int courseId) {
		System.out.println("inside");
		return userCourseEnrollemet.rejectUserCourseEnrollRequest(userId, courseId);
	}

}