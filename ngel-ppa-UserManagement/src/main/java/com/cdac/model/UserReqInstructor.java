package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user_req_instructor database table.
 * 
 */
@Entity
@Table(name="user_req_instructor")
@NamedQuery(name="UserReqInstructor.findAll", query="SELECT u FROM UserReqInstructor u")
public class UserReqInstructor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private String userId;

	private Timestamp regtime;

	private String remarks;

	private String status;

	public UserReqInstructor() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getRegtime() {
		return this.regtime;
	}

	public void setRegtime(Timestamp regtime) {
		this.regtime = regtime;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}