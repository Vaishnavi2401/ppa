package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the designation_master database table.
 * 
 */
@Entity
@Table(name="designation_master")
@NamedQuery(name="DesignationMaster.findAll", query="SELECT d FROM DesignationMaster d")
public class DesignationMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="desg_id")
	private int desgId;

	private String designation;

	//bi-directional many-to-one association to LearnerMaster
	//@OneToMany(mappedBy="designationMaster")
	//private List<LearnerMaster> learnerMasters;

	public DesignationMaster() {
	}

	public int getDesgId() {
		return this.desgId;
	}

	public void setDesgId(int desgId) {
		this.desgId = desgId;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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
	 * learnerMaster.setDesignationMaster(this);
	 * 
	 * return learnerMaster; }
	 * 
	 * public LearnerMaster removeLearnerMaster(LearnerMaster learnerMaster) {
	 * getLearnerMasters().remove(learnerMaster);
	 * learnerMaster.setDesignationMaster(null);
	 * 
	 * return learnerMaster; }
	 */

}