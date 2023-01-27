package com.cdac.coursecatalouge.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user_tenant_course_map database table.
 * 
 */
@Entity
@Table(name="user_tenant_course_map")
@NamedQuery(name="UserTenantCourseMap.findAll", query="SELECT u FROM UserTenantCourseMap u")
public class UserTenantCourseMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserTenantCourseMapPK id;

	@Column(name="completion_date")
	private Timestamp completionDate;

	@Column(name="course_end_date")
	private Timestamp courseEndDate;

	@Column(name="course_start_date")
	private Timestamp courseStartDate;

	@Column(name="enroll_approv_rejec_date")
	private Timestamp enrollApprovRejecDate;

	@Column(name="enroll_req_date")
	private Timestamp enrollReqDate;

	//bi-directional many-to-one association to TenantCourseDetail
	@ManyToOne ()
	@JoinColumns({
		@JoinColumn(name="course_id", referencedColumnName="COURSE_ID",insertable=false, updatable=false),
		@JoinColumn(name="tenant_id", referencedColumnName="TENANT_ID",insertable=false, updatable=false)
		})
	private TenantCourseDetail tenantCourseDetail;

	//bi-directional many-to-one association to UserRole
	@ManyToOne
	@JoinColumn(name="role_id",insertable=false, updatable=false)
	private UserRole userRole;

	public UserTenantCourseMap() {
	}

	public UserTenantCourseMapPK getId() {
		return this.id;
	}

	public void setId(UserTenantCourseMapPK id) {
		this.id = id;
	}

	

	public Timestamp getCompletionDate() {
		return this.completionDate;
	}

	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
	}

	public Timestamp getCourseEndDate() {
		return this.courseEndDate;
	}

	public void setCourseEndDate(Timestamp courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public Timestamp getCourseStartDate() {
		return this.courseStartDate;
	}

	public void setCourseStartDate(Timestamp courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public Timestamp getEnrollApprovRejecDate() {
		return this.enrollApprovRejecDate;
	}

	public void setEnrollApprovRejecDate(Timestamp enrollApprovRejecDate) {
		this.enrollApprovRejecDate = enrollApprovRejecDate;
	}

	public Timestamp getEnrollReqDate() {
		return this.enrollReqDate;
	}

	public void setEnrollReqDate(Timestamp enrollReqDate) {
		this.enrollReqDate = enrollReqDate;
	}

	public TenantCourseDetail getTenantCourseDetail() {
		return this.tenantCourseDetail;
	}

	public void setTenantCourseDetail(TenantCourseDetail tenantCourseDetail) {
		this.tenantCourseDetail = tenantCourseDetail;
	}

	public UserRole getUserRole() {
		return this.userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}