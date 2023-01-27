package com.cdac.feedback.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.feedback.models.FeedbackQuestionMap;
import com.cdac.feedback.models.FeedbackQuestionMapPK;

@Repository
public interface FeedbackQuestionMapRepo extends JpaRepository<FeedbackQuestionMap, FeedbackQuestionMapPK>{

	
	List<FeedbackQuestionMap> findByIdFeedbackId(int feedbackId);
}
