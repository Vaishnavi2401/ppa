package com.cdac.coursecatalouge.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_tenant_course_map database table.
 * 
 */
@Embeddable
public class UserTenantCourseMapPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private String userId;

	@Column(name="course_id", insertable=false, updatable=false)
	private int courseId;

	@Column(name="tenant_id", insertable=false, updatable=false)
	private int tenantId;

	@Column(name="role_id", insertable=false, updatable=false)
	private int roleId;

	public UserTenantCourseMapPK() {
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCourseId() {
		return this.courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getTenantId() {
		return this.tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	public int getRoleId() {
		return this.roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserTenantCourseMapPK)) {
			return false;
		}
		UserTenantCourseMapPK castOther = (UserTenantCourseMapPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& (this.courseId == castOther.courseId)
			&& (this.tenantId == castOther.tenantId)
			&& (this.roleId == castOther.roleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.courseId;
		hash = hash * prime + this.tenantId;
		hash = hash * prime + this.roleId;
		
		return hash;
	}
}