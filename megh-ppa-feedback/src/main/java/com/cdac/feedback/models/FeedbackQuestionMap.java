package com.cdac.feedback.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the feedback_question_map database table.
 * 
 */
@Entity
@Table(name="feedback_question_map")
@NamedQuery(name="FeedbackQuestionMap.findAll", query="SELECT f FROM FeedbackQuestionMap f")
public class FeedbackQuestionMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FeedbackQuestionMapPK id;

	@Column(name="map_id")
	private int mapId;

	//bi-directional many-to-one association to FeedbackMaster
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="feedback_id",insertable = false,updatable = false)
	private FeedbackMaster feedbackMaster;

	//bi-directional many-to-one association to QuestionMaster
	@ManyToOne
	@JoinColumn(name="question_id",insertable = false,updatable = false)
	private QuestionMaster questionMaster;

	public FeedbackQuestionMap() {
		/*Empty Constructor*/

	}

	public FeedbackQuestionMapPK getId() {
		return this.id;
	}

	public void setId(FeedbackQuestionMapPK id) {
		this.id = id;
	}

	public int getMapId() {
		return this.mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
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