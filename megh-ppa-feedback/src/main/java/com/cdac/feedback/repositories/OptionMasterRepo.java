package com.cdac.feedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.feedback.models.OptionsMaster;
import com.cdac.feedback.models.QuestionMaster;

@Repository
public interface OptionMasterRepo extends JpaRepository<OptionsMaster, Long>{
	
	void deleteByQuestionMaster(QuestionMaster questionMaster);

}
