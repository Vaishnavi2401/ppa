package com.cdac.feedback.dto;

import java.util.List;

import com.cdac.feedback.models.FeedbackMaster;
import com.cdac.feedback.models.FeedbackQuestionMap;
import com.cdac.feedback.models.QuestionMaster;
import lombok.Data;

@Data
public class ResponseCount {
	
	private Object question_id;
	
	private String question;
	
	private String questionType;
	
	private String mandatory;
	
	private List<OptionCount> optionCount;
	
	
	
//	private Object question_id ;
//	
//	private String question;
//	
//	private String questionType;
//	
//	private String mandatory;
	
	//private List<FeedbackQuestionMap> feedbackQuestionMaps;
	
	//private List<QuestionMaster> questionMaster;
	
//	private List<Count> count_obj;
	
	//private Object count ;
	
	//private String feedback_Response;
//	
//	
//	
//	public ResponseCount(Object count, String feedback_Response, Object question_id) {
//		super();
//		this.count = count;
//		this.feedback_Response = feedback_Response;
//		this.question_id = question_id;
//	}

	public ResponseCount() {
		super();
		// TODO Auto-generated constructor stub
	}	

}