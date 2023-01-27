package com.cdac.feedback.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the type_master database table.
 * 
 */
@Entity
@Table(name="type_master")
@NamedQuery(name="TypeMaster.findAll", query="SELECT t FROM TypeMaster t")
public class TypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="type_master_id")
	private int typeMasterId;

	@Column(name="type_description")
	private String typeDescription;

	@Column(name="type_title")
	private String typeTitle;

	@Column(name="update_by")
	private String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@Column(name="updated_on")
	private Date updatedOn;

	//bi-directional many-to-one association to FeedbackMaster
	@JsonIgnore
	@OneToMany(mappedBy="typeMaster")
	private List<FeedbackMaster> feedbackMasters;

	
	public TypeMaster() {
		/* Empty Constructor*/
	}

	public int getTypeMasterId() {
		return this.typeMasterId;
	}

	public void setTypeMasterId(int typeMasterId) {
		this.typeMasterId = typeMasterId;
	}

	public String getTypeDescription() {
		return this.typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public String getTypeTitle() {
		return this.typeTitle;
	}

	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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

	public FeedbackMaster addFeedbackMaster(FeedbackMaster feedbackMaster) {
		getFeedbackMasters().add(feedbackMaster);
		feedbackMaster.setTypeMaster(this);

		return feedbackMaster;
	}

	public FeedbackMaster removeFeedbackMaster(FeedbackMaster feedbackMaster) {
		getFeedbackMasters().remove(feedbackMaster);
		feedbackMaster.setTypeMaster(null);

		return feedbackMaster;
	}

}