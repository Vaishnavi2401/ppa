package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the org_master database table.
 * 
 */
@Entity
@Table(name="org_master")
@NamedQuery(name="OrgMaster.findAll", query="SELECT o FROM OrgMaster o")
public class OrgMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_ID")
	private int orgId;

	@Column(name="ORG_ADDRESS")
	private String orgAddress;

	@Column(name="ORG_CODE")
	private String orgCode;

	@Column(name="ORG_NAME")
	private String orgName;

	//bi-directional many-to-one association to UserMaster
	@OneToMany(mappedBy="orgMaster")
	private List<UserMaster> userMasters;

	public OrgMaster() {
	}

	public int getOrgId() {
		return this.orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgAddress() {
		return this.orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<UserMaster> getUserMasters() {
		return this.userMasters;
	}

	public void setUserMasters(List<UserMaster> userMasters) {
		this.userMasters = userMasters;
	}

	public UserMaster addUserMaster(UserMaster userMaster) {
		getUserMasters().add(userMaster);
		userMaster.setOrgMaster(this);

		return userMaster;
	}

	public UserMaster removeUserMaster(UserMaster userMaster) {
		getUserMasters().remove(userMaster);
		userMaster.setOrgMaster(null);

		return userMaster;
	}

}