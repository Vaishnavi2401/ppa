package com.cdac.feedback.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the question_master database table.
 * 
 */
@Entity
@Table(name="question_master")
@NamedQuery(name="QuestionMaster.findAll", query="SELECT q FROM QuestionMaster q")
public class QuestionMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	private String mandatory;

	private String question;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="question_id")
	private int questionId;

	@Column(name="question_type")
	private String questionType;

	//todo make it one to many 
	@Column(name="type_id")
	private int typeId;
	

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	  @JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@Column(name="updated_on")
	private Date updatedOn;

	//bi-directional many-to-many association to FeedbackMaster
	@JsonIgnore
	@ManyToMany(mappedBy="questionMasters")
	private List<FeedbackMaster> feedbackMasters;

	//bi-directional many-to-one association to FeedbackQuestionMap
	@JsonIgnore
	@OneToMany(mappedBy="questionMaster")
	private List<FeedbackQuestionMap> feedbackQuestionMaps;

	//bi-directional many-to-one association to OptionsMaster
	@OneToMany(mappedBy="questionMaster")
	private List<OptionsMaster> optionsMasters;

	//bi-directional many-to-one association to ResponseMaster
	@JsonIgnore
	@OneToMany(mappedBy="questionMaster")
	private List<ResponseMaster> responseMasters;

	public QuestionMaster() {
	}

	public String getMandatory() {
		return this.mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public int getTypeId() {
		return this.typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
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
	public List<FeedbackMaster> getFeedbackMasters() {
		return this.feedbackMasters;
	}

	public void setFeedbackMasters(List<FeedbackMaster> feedbackMasters) {
		this.feedbackMasters = feedbackMasters;
	}

	public List<FeedbackQuestionMap> getFeedbackQuestionMaps() {
		return this.feedbackQuestionMaps;
	}

	public void setFeedbackQuestionMaps(List<FeedbackQuestionMap> feedbackQuestionMaps) {
		this.feedbackQuestionMaps = feedbackQuestionMaps;
	}

	public FeedbackQuestionMap addFeedbackQuestionMap(FeedbackQuestionMap feedbackQuestionMap) {
		getFeedbackQuestionMaps().add(feedbackQuestionMap);
		feedbackQuestionMap.setQuestionMaster(this);

		return feedbackQuestionMap;
	}

	public FeedbackQuestionMap removeFeedbackQuestionMap(FeedbackQuestionMap feedbackQuestionMap) {
		getFeedbackQuestionMaps().remove(feedbackQuestionMap);
		feedbackQuestionMap.setQuestionMaster(null);

		return feedbackQuestionMap;
	}

	public List<OptionsMaster> getOptionsMasters() {
		return this.optionsMasters;
	}

	public void setOptionsMasters(List<OptionsMaster> optionsMasters) {
		this.optionsMasters = optionsMasters;
	}

	public OptionsMaster addOptionsMaster(OptionsMaster optionsMaster) {
		getOptionsMasters().add(optionsMaster);
		optionsMaster.setQuestionMaster(this);

		return optionsMaster;
	}

	public OptionsMaster removeOptionsMaster(OptionsMaster optionsMaster) {
		getOptionsMasters().remove(optionsMaster);
		optionsMaster.setQuestionMaster(null);

		return optionsMaster;
	}

	public List<ResponseMaster> getResponseMasters() {
		return this.responseMasters;
	}

	public void setResponseMasters(List<ResponseMaster> responseMasters) {
		this.responseMasters = responseMasters;
	}

	public ResponseMaster addResponseMaster(ResponseMaster responseMaster) {
		getResponseMasters().add(responseMaster);
		responseMaster.setQuestionMaster(this);

		return responseMaster;
	}

	public ResponseMaster removeResponseMaster(ResponseMaster responseMaster) {
		getResponseMasters().remove(responseMaster);
		responseMaster.setQuestionMaster(null);

		return responseMaster;
	}

}