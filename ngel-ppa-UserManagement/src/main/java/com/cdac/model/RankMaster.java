package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rank_master database table.
 * 
 */
@Entity
@Table(name="rank_master")
@NamedQuery(name="RankMaster.findAll", query="SELECT r FROM RankMaster r")
public class RankMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RANK_ID")
	private int rankId;

	@Column(name="RANK_NAME")
	private String rankName;

	//bi-directional many-to-one association to UserMaster
	@OneToMany(mappedBy="rankMaster")
	private List<UserMaster> userMasters;

	public RankMaster() {
	}

	public int getRankId() {
		return this.rankId;
	}

	public void setRankId(int rankId) {
		this.rankId = rankId;
	}

	public String getRankName() {
		return this.rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public List<UserMaster> getUserMasters() {
		return this.userMasters;
	}

	public void setUserMasters(List<UserMaster> userMasters) {
		this.userMasters = userMasters;
	}

	public UserMaster addUserMaster(UserMaster userMaster) {
		getUserMasters().add(userMaster);
		userMaster.setRankMaster(this);

		return userMaster;
	}

	public UserMaster removeUserMaster(UserMaster userMaster) {
		getUserMasters().remove(userMaster);
		userMaster.setRankMaster(null);

		return userMaster;
	}

}