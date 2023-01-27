package com.cdac.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.dto.StatewiseTraineeCount;
import com.cdac.model.LearnerMaster;

@Repository
public interface LearnerDAO extends JpaRepository<LearnerMaster, String> {

	
	List<LearnerMaster> findByEmail(String emailid);
	List<LearnerMaster> findByMobile(Long mobile);
	List<LearnerMaster> findByActivationToken(String activationToken);
	List<LearnerMaster> findByAuthenticationId(int authenticationId);
	List<LearnerMaster> findByDob(Date searchkey);
	List<LearnerMaster> findByLearnerUsernameIn(List<String> userIds);
	List<LearnerMaster> findByLearnerUsername(String traineeUsername);
	List<LearnerMaster> findByStatus(String status);
	
	@Query(value = "SELECT STATE_ID, count(*) FROM `learner_master` GROUP by `STATE_ID`", nativeQuery = true)
	List<LearnerMaster> getApprovedByOnlyCalendarYear(String calendarYear); 
	
	 @Query(value = "SELECT c.STATE_ID AS state,COUNT(*) AS total FROM learner_master AS c GROUP BY c.STATE_ID ORDER BY c.STATE_ID DESC", nativeQuery = true)
	   List<LearnerStateCount> getAllstatesLearnerCount();
	 
	 @Modifying  
	 //@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	 @Query(value = "UPDATE learner_master SET LEARNER_USERNAME =:newid WHERE LEARNER_USERNAME = :oldid", nativeQuery = true)
	 void updateUsername(String oldid, String newid);
	
	
	
}
