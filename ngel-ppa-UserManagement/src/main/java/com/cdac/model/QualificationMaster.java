package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the qualification_master database table.
 * 
 */
@Entity
@Table(name="qualification_master")
@NamedQuery(name="QualificationMaster.findAll", query="SELECT q FROM QualificationMaster q")
public class QualificationMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="qual_id")
	private int qualId;

	private String qualification;

	//bi-directional many-to-one association to LearnerMaster
	//@OneToMany(mappedBy="qualificationMaster")
	//private List<LearnerMaster> learnerMasters;

	public QualificationMaster() {
	}

	public int getQualId() {
		return this.qualId;
	}

	public void setQualId(int qualId) {
		this.qualId = qualId;
	}

	public String getQualification() {
		return this.qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	/*
	 * public List<LearnerMaster> getLearnerMasters() { return this.learnerMasters;
	 * }
	 * 
	 * public void setLearnerMasters(List<LearnerMaster> learnerMasters) {
	 * this.learnerMasters = learnerMasters; }
	 * 
	 * public LearnerMaster addLearnerMaster(LearnerMaster learnerMaster) {
	 * getLearnerMasters().add(learnerMaster);
	 * learnerMaster.setQualificationMaster(this);
	 * 
	 * return learnerMaster; }
	 * 
	 * public LearnerMaster removeLearnerMaster(LearnerMaster learnerMaster) {
	 * getLearnerMasters().remove(learnerMaster);
	 * learnerMaster.setQualificationMaster(null);
	 * 
	 * return learnerMaster; }
	 */

}