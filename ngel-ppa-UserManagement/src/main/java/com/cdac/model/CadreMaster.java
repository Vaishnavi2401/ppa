package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cadre_master database table.
 * 
 */
@Entity
@Table(name="cadre_master")
@NamedQuery(name="CadreMaster.findAll", query="SELECT c FROM CadreMaster c")
public class CadreMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cadre_id")
	private int cadreId;

	private String cadre;

	//bi-directional many-to-one association to LearnerMaster
	//@OneToMany(mappedBy="cadreMaster")
	//private List<LearnerMaster> learnerMasters;

	public CadreMaster() {
	}

	public int getCadreId() {
		return this.cadreId;
	}

	public void setCadreId(int cadreId) {
		this.cadreId = cadreId;
	}

	public String getCadre() {
		return this.cadre;
	}

	public void setCadre(String cadre) {
		this.cadre = cadre;
	}

	/*
	 * public List<LearnerMaster> getLearnerMasters() { return this.learnerMasters;
	 * }
	 * 
	 * public void setLearnerMasters(List<LearnerMaster> learnerMasters) {
	 * this.learnerMasters = learnerMasters; }
	 * 
	 * public LearnerMaster addLearnerMaster(LearnerMaster learnerMaster) {
	 * getLearnerMasters().add(learnerMaster); learnerMaster.setCadreMaster(this);
	 * 
	 * return learnerMaster; }
	 * 
	 * public LearnerMaster removeLearnerMaster(LearnerMaster learnerMaster) {
	 * getLearnerMasters().remove(learnerMaster);
	 * learnerMaster.setCadreMaster(null);
	 * 
	 * return learnerMaster; }
	 */

}