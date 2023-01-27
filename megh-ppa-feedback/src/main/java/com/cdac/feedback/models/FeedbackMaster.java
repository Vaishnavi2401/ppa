package com.cdac.feedback.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the feedback_master database table.
 * 
 */
@Entity
@Table(name="feedback_master")
@NamedQuery(name="FeedbackMaster.findAll", query="SELECT f FROM FeedbackMaster f")
public class FeedbackMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="feedback_id")
	private int feedbackId;

	private String description;

	@Column(name="feedback_title")
	private String feedbackTitle;

	private int id;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;

	//bi-directional many-to-many association to QuestionMaster
	@ManyToMany
	@JsonIgnore
	@JoinTable(
		name="feedback_question_map"
		, joinColumns={
			@JoinColumn(name="feedback_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="question_id")
			}
		)
	private List<QuestionMaster> questionMasters;

	//bi-directional many-to-one association to TypeMaster
	@ManyToOne
	@JoinColumn(name="type_master_id")
	private TypeMaster typeMaster;

	//bi-directional many-to-one association to FeedbackQuestionMap
	@OneToMany(mappedBy="feedbackMaster")
	@JsonIgnore
	private List<FeedbackQuestionMap> feedbackQuestionMaps;

	//bi-directional many-to-one association to ResponseMaster
	@JsonIgnore
	@OneToMany(mappedBy="feedbackMaster")
	private List<ResponseMaster> responseMasters;

	public FeedbackMaster() {
		/*Empty Constructor*/
	}

	public int getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFeedbackTitle() {
		return this.feedbackTitle;
	}

	public void setFeedbackTitle(String feedbackTitle) {
		this.feedbackTitle = feedbackTitle;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<QuestionMaster> getQuestionMasters() {
		return this.questionMasters;
	}

	public void setQuestionMasters(List<QuestionMaster> questionMasters) {
		this.questionMasters = questionMasters;
	}

	public TypeMaster getTypeMaster() {
		return this.typeMaster;
	}

	public void setTypeMaster(TypeMaster typeMaster) {
		this.typeMaster = typeMaster;
	}

	public List<FeedbackQuestionMap> getFeedbackQuestionMaps() {
		return this.feedbackQuestionMaps;
	}

	public void setFeedbackQuestionMaps(List<FeedbackQuestionMap> feedbackQuestionMaps) {
		this.feedbackQuestionMaps = feedbackQuestionMaps;
	}

	public FeedbackQuestionMap addFeedbackQuestionMap(FeedbackQuestionMap feedbackQuestionMap) {
		getFeedbackQuestionMaps().add(feedbackQuestionMap);
		feedbackQuestionMap.setFeedbackMaster(this);

		return feedbackQuestionMap;
	}

	public FeedbackQuestionMap removeFeedbackQuestionMap(FeedbackQuestionMap feedbackQuestionMap) {
		getFeedbackQuestionMaps().remove(feedbackQuestionMap);
		feedbackQuestionMap.setFeedbackMaster(null);

		return feedbackQuestionMap;
	}

	public List<ResponseMaster> getResponseMasters() {
		return this.responseMasters;
	}

	public void setResponseMasters(List<ResponseMaster> responseMasters) {
		this.responseMasters = responseMasters;
	}

	public ResponseMaster addResponseMaster(ResponseMaster responseMaster) {
		getResponseMasters().add(responseMaster);
		responseMaster.setFeedbackMaster(this);

		return responseMaster;
	}

	public ResponseMaster removeResponseMaster(ResponseMaster responseMaster) {
		getResponseMasters().remove(responseMaster);
		responseMaster.setFeedbackMaster(null);

		return responseMaster;
	}

}