package com.cdac.feedback.services;

import java.util.List;
import java.util.Optional;

import com.cdac.feedback.dto.CustomResponeMasterDto;
import com.cdac.feedback.dto.FeedbackMasterDto;
import com.cdac.feedback.dto.FeedbackQuestionMapDto;
import com.cdac.feedback.dto.QuestionMasterDto;
import com.cdac.feedback.dto.ResponesMasterDto;
import com.cdac.feedback.dto.ResponseCount;
import com.cdac.feedback.dto.TypeMasterDto;
import com.cdac.feedback.models.FeedbackMaster;
import com.cdac.feedback.models.FeedbackQuestionMap;
import com.cdac.feedback.models.QuestionMaster;
import com.cdac.feedback.models.ResponseMaster;
import com.cdac.feedback.models.TypeMaster;

public interface FeedbackServices {

	//TypeMaster 
	
	TypeMaster addTypeMaster(TypeMasterDto typeMasterPojo);
	TypeMaster updateTypeMaster(TypeMasterDto typeMasterPojo);
	List<TypeMaster> getAllTypeMasterDetails();
	Optional<TypeMaster> getTypeById(int id);
	Boolean getTypeByName(String name); // to check if type name is already in the database
	void deleteTypeById(int typeId);

	
	
	//QuestionMaster
	
	
	QuestionMaster addQuestion(QuestionMasterDto questionMasterDto);
	QuestionMaster updateQuestion(QuestionMasterDto questionMasterDto);
	List<QuestionMaster> getQuestionByIds(List<Integer> questionIds); //can be used for getting single & multiple questionMaster details
	void deleteQuestionById(int questionId);
	List<QuestionMaster> getAllQuestionDetails();
	List<QuestionMaster> getQuestionByTypeId(int typeId);
	List<QuestionMaster> getQuestionByTypeIdAndUpdatedBy(int typeId, String updatedBy);

	
	//FeedbackMaster
	

	
	FeedbackMaster addFeeback(FeedbackMasterDto feedbackMasterDto);
	FeedbackMaster updateFeeback(FeedbackMasterDto feedbackMasterDto);
	Optional<FeedbackMaster> getFeedBackById(int feedbackId);
	Optional<FeedbackMaster> getFeedBackByTypeAndId(int type ,int id);
	List<FeedbackMaster> getAllFeedbackDetails();
	void deleteFeedback(int feedbackId);
	

		
	
	//FeedbackQuestionMap
	
	void  addFeedbackMap(FeedbackQuestionMapDto feedbackQuestionMapDto);
	void updateFeedbackMap(FeedbackQuestionMapDto feedbackQuestionMapDto); //updateByFeedbackId - remove and again add the mappings
	void deleteMapByFeedbackId(int feedbackId);
	List<FeedbackQuestionMap> getQuestionsByFeedbackId(int feedbackId);


	//ResponseMaster
	

	void addResponses(List<ResponesMasterDto> responseMasterDtos); //can be used for adding single & multiple responses
	List<CustomResponeMasterDto> findByFeedbackId(int feedbackId);
	List<ResponseMaster> findByFeedbackIdAndFeedbackBy(int feedbackId,String feedBackBy);
	
	
	// functionality for directly entering details into all tables - writing for
		// Meghsikshak

		void addFeedabackAndQuestion(QuestionMasterDto questionMasterDto);
		
		List<ResponseCount> getFeedBackSummary (int type_id , int Id);
	
}
