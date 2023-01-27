package com.cdac.Repository;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdac.Entity.Certificate;
 
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

	Certificate findByCertificateId(int certificate_id);
	
	@Query(value = "SELECT * FROM certificate lc where lc.user_id=:userId and lc.course_id=:courseId",nativeQuery = true)
	Certificate findByUserIdCourseId(String userId, int courseId);
	//int getMaxsno(@Param("userId") String userId,  @Param("sessionId") String sessionId);
		
	//Certificate findByUserIdAndTemplate_TemplateId(String user_id,int t);
	/* public Assignment findByAssignmentId(int assign_id); */
	/*
	 * public Assignment findByUserIdAndSessionId(String id,String s);
	 * 
	 * public List<Assignment> findByUserIdAndCourseId(String userid, String
	 * courseid);
	 */
	
}
