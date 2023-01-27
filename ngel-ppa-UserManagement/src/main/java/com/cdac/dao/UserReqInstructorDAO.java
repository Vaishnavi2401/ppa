package com.cdac.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdac.model.LearnerMaster;
import com.cdac.model.UserReqInstructor;


public interface UserReqInstructorDAO extends JpaRepository<UserReqInstructor, String>{

	public final static String getuserlistByStatus = "select * from `user_req_instructor` where status  =:status";
	
	@Query(value = getuserlistByStatus, nativeQuery = true)
	List<UserReqInstructor> findByreqStatus(String status);
	
	List<UserReqInstructor> findByuserId(String userId);
	
	//List<UserReqInstructor> findByuserId(String traineeUsername);
}
