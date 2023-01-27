package com.cdac.feedback.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.feedback.models.ResponseMaster;

@Repository
public interface ResponseMasterRepo extends JpaRepository<ResponseMaster, Long>{

	
	List<ResponseMaster> findByIdFeedbackId(int feedbackId);
	List<ResponseMaster> findByIdFeedbackIdAndIdFeedbackBy(int feedbackId,String feedbackBy);
	
	@Query(value = "select DISTINCT feedback_by from response_master where feedback_id=?1", nativeQuery = true)
	List<String> getAttemptedUserListByFeedbackId(int feedbackId);
	
//	@Query(value = "select DISTINCT feedback_by from response_master where feedback_id=?1", nativeQuery = true)
//	List<String> getAttemptedUserListByFeedbackId(int feedbackId);

	@Query(value = "SELECT count(feedback_response), feedback_response, question_id FROM response_master where question_id=?1 group by feedback_response", nativeQuery = true)
	List<Object[]> getResponseCountByQuestionId(int questionId);

	
	//feedback_id=?1 and
	// @Query(value = "SELECT
	// response_master.question_id,response_master.feedback_id FROM
	// response_master,feedback_master,feedback_question_map,question_master WHERE
	// (response_master.feedback_id =feedback_master.feedback_id AND
	// feedback_master.id=?1 AND question_master.type_id=?2 AND
	// feedback_master.feedback_id=feedback_question_map.feedback_id AND
	// response_master.question_id = question_master.question_id AND
	// (question_master.question_type='TF' OR question_master.question_type='SC' ))
	// GROUP BY response_master.question_id" , nativeQuery = true )
	// List<Integer[]> getResponseQuestionIdByCourseAndType(int id , int typeid);

	@Query(value = "SELECT q.question_id FROM question_master q WHERE q.question_id in (SELECT fqm.question_id FROM feedback_question_map fqm WHERE fqm.feedback_id in (SELECT fm.feedback_id FROM feedback_master fm WHERE fm.id=?1 and fm.type_master_id=?2)) and q.question_type in ('SC','TF')", nativeQuery = true)
	List<Integer[]> getResponseQuestionIdByCourseAndType(int id, int typeid);

	//Optional<FeedbackQuestionMap> findById(int id);
	
}
