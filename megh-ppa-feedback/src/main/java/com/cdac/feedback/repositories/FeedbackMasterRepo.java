package com.cdac.feedback.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.feedback.models.FeedbackMaster;
import com.cdac.feedback.models.TypeMaster;

@Repository
public interface FeedbackMasterRepo extends JpaRepository<FeedbackMaster, Integer> {

	
	Optional<FeedbackMaster> findByIdAndTypeMaster(int id,TypeMaster typeMaster);
}
