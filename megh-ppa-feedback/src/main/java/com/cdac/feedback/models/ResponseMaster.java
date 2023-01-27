package com.cdac.feedback.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the response_master database table.
 * 
 */
@Entity
@Table(name="response_master")
@NamedQuery(name="ResponseMaster.findAll", query="SELECT r FROM ResponseMaster r")
public class ResponseMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ResponseMasterPK id;

	
	@Column(name="feedback_response")
	private String feedbackResponse;

	@Column(name="response_id")
	private int responseId;

	//bi-directional many-to-one association to FeedbackMaster
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="feedback_id",insertable = false,updatable = false)
	private FeedbackMaster feedbackMaster;

	//bi-directional many-to-one association to QuestionMaster
	@ManyToOne
	@JoinColumn(name="question_id",insertable = false,updatable = false)
	private QuestionMaster questionMaster;

	public ResponseMaster() {
		/* Empty Constructor*/
	}

	public ResponseMasterPK getId() {
		return this.id;
	}

	public void setId(ResponseMasterPK id) {
		this.id = id;
	}

	
	public String getFeedbackResponse() {
		return this.feedbackResponse;
	}

	public void setFeedbackResponse(String feedbackResponse) {
		this.feedbackResponse = feedbackResponse;
	}

	public int getResponseId() {
		return this.responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public FeedbackMaster getFeedbackMaster() {
		return this.feedbackMaster;
	}

	public void setFeedbackMaster(FeedbackMaster feedbackMaster) {
		this.feedbackMaster = feedbackMaster;
	}

	public QuestionMaster getQuestionMaster() {
		return this.questionMaster;
	}

	public void setQuestionMaster(QuestionMaster questionMaster) {
		this.questionMaster = questionMaster;
	}

}