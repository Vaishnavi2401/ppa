package com.cdac.feedback.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.feedback.models.QuestionMaster;

@Repository
public interface QuestionMasterRepo extends JpaRepository<QuestionMaster, Integer>{

	List<QuestionMaster> findByQuestionIdIn(List<Integer> questionIds);
	
	//TODO when updated Models change this from TypeId to TypeMaster
	List<QuestionMaster> findByTypeId(int typeId);
	
	List<QuestionMaster> findByTypeIdAndUpdatedBy(int typeId, String updatedBy);	

	
	
	List<QuestionMaster> findByQuestionId(int id);
	
	@Query(value = "SELECT * FROM question_master WHERE question_id=?1" , nativeQuery = true)
	List<QuestionMaster> findByQId(int id);
}
