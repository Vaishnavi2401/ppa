package com.cdac.feedback.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The primary key class for the response_master database table.
 * 
 */
@Embeddable
public class ResponseMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="feedback_id", insertable=false, updatable=false)
	private int feedbackId;

	@Column(name="question_id", insertable=false, updatable=false)
	private int questionId;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="feedback_date")
	private Date feedbackDate;

	
	@Column(name="feedback_by")
	private String feedbackBy;

	public ResponseMasterPK() {
		/* Empty Constructor*/
	}
	public int getFeedbackId() {
		return this.feedbackId;
	}
	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}
	public int getQuestionId() {
		return this.questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getFeedbackBy() {
		return this.feedbackBy;
	}
	public void setFeedbackBy(String feedbackBy) {
		this.feedbackBy = feedbackBy;
	}
	
	public Date getFeedbackDate() {
		return this.feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ResponseMasterPK)) {
			return false;
		}
		ResponseMasterPK castOther = (ResponseMasterPK)other;
		return 
			(this.feedbackId == castOther.feedbackId)
			&& (this.questionId == castOther.questionId)
			&& this.feedbackBy.equals(castOther.feedbackBy);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.feedbackId;
		hash = hash * prime + this.questionId;
		hash = hash * prime + this.feedbackBy.hashCode();
		
		return hash;
	}
}