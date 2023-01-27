package com.cdac.coursecatalouge.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tenant_course_details database table.
 * 
 */
@Embeddable
public class TenantCourseDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TENANT_ID")
	private int tenantId;

	@Column(name="COURSE_ID")
	private int courseId;

	public TenantCourseDetailPK() {
	}
	public int getTenantId() {
		return this.tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	public int getCourseId() {
		return this.courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TenantCourseDetailPK)) {
			return false;
		}
		TenantCourseDetailPK castOther = (TenantCourseDetailPK)other;
		return 
			(this.tenantId == castOther.tenantId)
			&& (this.courseId == castOther.courseId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.tenantId;
		hash = hash * prime + this.courseId;
		
		return hash;
	}
}