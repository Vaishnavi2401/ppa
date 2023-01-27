package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the state_master database table.
 * 
 */
@Entity
@Table(name="state_master")
@NamedQuery(name="StateMaster.findAll", query="SELECT s FROM StateMaster s")
public class StateMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="STATE_ID")
	private int stateId;

	@Column(name="STATE_NAME")
	private String stateName;

	@JsonIgnore
	//bi-directional many-to-one association to DistrictMaster
	@OneToMany(mappedBy="stateMaster")
	private List<DistrictMaster> districtMasters;

	@JsonIgnore
	//bi-directional many-to-one association to LearnerMaster
	@OneToMany(mappedBy="stateMaster")
	private List<LearnerMaster> learnerMasters;

	@JsonIgnore
	//bi-directional many-to-one association to CountryMaster
	@ManyToOne
	@JoinColumn(name="COUNTRY_ID")
	private CountryMaster countryMaster;

	public StateMaster() {
	}

	public int getStateId() {
		return this.stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<DistrictMaster> getDistrictMasters() {
		return this.districtMasters;
	}

	public void setDistrictMasters(List<DistrictMaster> districtMasters) {
		this.districtMasters = districtMasters;
	}

	public DistrictMaster addDistrictMaster(DistrictMaster districtMaster) {
		getDistrictMasters().add(districtMaster);
		districtMaster.setStateMaster(this);

		return districtMaster;
	}

	public DistrictMaster removeDistrictMaster(DistrictMaster districtMaster) {
		getDistrictMasters().remove(districtMaster);
		districtMaster.setStateMaster(null);

		return districtMaster;
	}

	public List<LearnerMaster> getLearnerMasters() {
		return this.learnerMasters;
	}

	public void setLearnerMasters(List<LearnerMaster> learnerMasters) {
		this.learnerMasters = learnerMasters;
	}

	public LearnerMaster addLearnerMaster(LearnerMaster learnerMaster) {
		getLearnerMasters().add(learnerMaster);
		learnerMaster.setStateMaster(this);

		return learnerMaster;
	}

	public LearnerMaster removeLearnerMaster(LearnerMaster learnerMaster) {
		getLearnerMasters().remove(learnerMaster);
		learnerMaster.setStateMaster(null);

		return learnerMaster;
	}

	public CountryMaster getCountryMaster() {
		return this.countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

}