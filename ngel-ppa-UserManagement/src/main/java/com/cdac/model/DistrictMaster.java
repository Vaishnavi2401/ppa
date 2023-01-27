package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the district_master database table.
 * 
 */
@Entity
@Table(name="district_master")
@NamedQuery(name="DistrictMaster.findAll", query="SELECT d FROM DistrictMaster d")
public class DistrictMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DISTRICT_ID")
	private int districtId;

	@Column(name="DISTRICT_NAME")
	private String districtName;

	@JsonIgnore
	//bi-directional many-to-one association to StateMaster
	@ManyToOne
	@JoinColumn(name="STATE_ID")
	private StateMaster stateMaster;

	@JsonIgnore
	//bi-directional many-to-one association to LearnerMaster
	@OneToMany(mappedBy="districtMaster")
	private List<LearnerMaster> learnerMasters;

	public DistrictMaster() {
	}

	public int getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public StateMaster getStateMaster() {
		return this.stateMaster;
	}

	public void setStateMaster(StateMaster stateMaster) {
		this.stateMaster = stateMaster;
	}

	public List<LearnerMaster> getLearnerMasters() {
		return this.learnerMasters;
	}

	public void setLearnerMasters(List<LearnerMaster> learnerMasters) {
		this.learnerMasters = learnerMasters;
	}

	public LearnerMaster addLearnerMaster(LearnerMaster learnerMaster) {
		getLearnerMasters().add(learnerMaster);
		learnerMaster.setDistrictMaster(this);

		return learnerMaster;
	}

	public LearnerMaster removeLearnerMaster(LearnerMaster learnerMaster) {
		getLearnerMasters().remove(learnerMaster);
		learnerMaster.setDistrictMaster(null);

		return learnerMaster;
	}

}