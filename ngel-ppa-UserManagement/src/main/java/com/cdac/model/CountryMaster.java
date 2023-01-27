package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the country_master database table.
 * 
 */
@Entity
@Table(name="country_master")
@NamedQuery(name="CountryMaster.findAll", query="SELECT c FROM CountryMaster c")
public class CountryMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COUNTRY_ID")
	private int countryId;

	@Column(name="COUNTRY_NAME")
	private String countryName;

	@JsonIgnore
	//bi-directional many-to-one association to LearnerMaster
	@OneToMany(mappedBy="countryMaster")
	private List<LearnerMaster> learnerMasters;

	@JsonIgnore
	//bi-directional many-to-one association to StateMaster
	@OneToMany(mappedBy="countryMaster")
	private List<StateMaster> stateMasters;

	public CountryMaster() {
	}

	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<LearnerMaster> getLearnerMasters() {
		return this.learnerMasters;
	}

	public void setLearnerMasters(List<LearnerMaster> learnerMasters) {
		this.learnerMasters = learnerMasters;
	}

	public LearnerMaster addLearnerMaster(LearnerMaster learnerMaster) {
		getLearnerMasters().add(learnerMaster);
		learnerMaster.setCountryMaster(this);

		return learnerMaster;
	}

	public LearnerMaster removeLearnerMaster(LearnerMaster learnerMaster) {
		getLearnerMasters().remove(learnerMaster);
		learnerMaster.setCountryMaster(null);

		return learnerMaster;
	}

	public List<StateMaster> getStateMasters() {
		return this.stateMasters;
	}

	public void setStateMasters(List<StateMaster> stateMasters) {
		this.stateMasters = stateMasters;
	}

	public StateMaster addStateMaster(StateMaster stateMaster) {
		getStateMasters().add(stateMaster);
		stateMaster.setCountryMaster(this);

		return stateMaster;
	}

	public StateMaster removeStateMaster(StateMaster stateMaster) {
		getStateMasters().remove(stateMaster);
		stateMaster.setCountryMaster(null);

		return stateMaster;
	}

}