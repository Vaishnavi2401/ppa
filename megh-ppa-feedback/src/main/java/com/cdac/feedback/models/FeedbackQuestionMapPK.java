package com.cdac.feedback.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the feedback_question_map database table.
 * 
 */
@Embeddable
public class FeedbackQuestionMapPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="feedback_id", insertable=false, updatable=false)
	private int feedbackId;

	@Column(name="question_id", insertable=false, updatable=false)
	private int questionId;

	public FeedbackQuestionMapPK() {
		/*Empty Constructor*/

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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FeedbackQuestionMapPK)) {
			return false;
		}
		FeedbackQuestionMapPK castOther = (FeedbackQuestionMapPK)other;
		return 
			(this.feedbackId == castOther.feedbackId)
			&& (this.questionId == castOther.questionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.feedbackId;
		hash = hash * prime + this.questionId;
		
		return hash;
	}
}