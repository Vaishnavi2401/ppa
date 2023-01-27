package com.cdac.coursecatalouge.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tenant_course_details database table.
 * 
 */
@Entity
@Table(name="tenant_course_details")
@NamedQuery(name="TenantCourseDetail.findAll", query="SELECT t FROM TenantCourseDetail t")
public class TenantCourseDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TenantCourseDetailPK id;

	private String category;

	@Column(name="CATEGORY_ID")
	private int categoryId;

	@Column(name="COMMENCEMENT_DATE")
	private Timestamp commencementDate;

	@Column(name="COURSE_CLOSING_DATE")
	private Timestamp courseClosingDate;

	@Lob
	@Column(name="COURSE_DESCRIPTION")
	private String courseDescription;

	@Column(name="COURSE_FEE")
	private int courseFee;

	@Lob
	@Column(name="COURSE_NAME")
	private String courseName;

	@Column(name="COURSE_TYPE")
	private String courseType;

	private int duration;

	@Column(name="ENROLL_EDATE")
	private Timestamp enrollEdate;

	@Column(name="ENROLL_SDATE")
	private Timestamp enrollSdate;

	@Lob
	@Column(name="IMAGE_URL")
	private String imageUrl;

	@Column(name="PUBLISH_DATE")
	private Timestamp publishDate;

	private String status;

	//bi-directional many-to-one association to UserTenantCourseMap
	@OneToMany(mappedBy="tenantCourseDetail")
	private List<UserTenantCourseMap> userTenantCourseMaps;

	public TenantCourseDetail() {
	}

	public TenantCourseDetailPK getId() {
		return this.id;
	}

	public void setId(TenantCourseDetailPK id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Timestamp getCommencementDate() {
		return this.commencementDate;
	}

	public void setCommencementDate(Timestamp commencementDate) {
		this.commencementDate = commencementDate;
	}

	public Timestamp getCourseClosingDate() {
		return this.courseClosingDate;
	}

	public void setCourseClosingDate(Timestamp courseClosingDate) {
		this.courseClosingDate = courseClosingDate;
	}

	public String getCourseDescription() {
		return this.courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public int getCourseFee() {
		return this.courseFee;
	}

	public void setCourseFee(int courseFee) {
		this.courseFee = courseFee;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseType() {
		return this.courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Timestamp getEnrollEdate() {
		return this.enrollEdate;
	}

	public void setEnrollEdate(Timestamp enrollEdate) {
		this.enrollEdate = enrollEdate;
	}

	public Timestamp getEnrollSdate() {
		return this.enrollSdate;
	}

	public void setEnrollSdate(Timestamp enrollSdate) {
		this.enrollSdate = enrollSdate;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Timestamp getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<UserTenantCourseMap> getUserTenantCourseMaps() {
		return this.userTenantCourseMaps;
	}

	public void setUserTenantCourseMaps(List<UserTenantCourseMap> userTenantCourseMaps) {
		this.userTenantCourseMaps = userTenantCourseMaps;
	}

	public UserTenantCourseMap addUserTenantCourseMap(UserTenantCourseMap userTenantCourseMap) {
		getUserTenantCourseMaps().add(userTenantCourseMap);
		userTenantCourseMap.setTenantCourseDetail(this);

		return userTenantCourseMap;
	}

	public UserTenantCourseMap removeUserTenantCourseMap(UserTenantCourseMap userTenantCourseMap) {
		getUserTenantCourseMaps().remove(userTenantCourseMap);
		userTenantCourseMap.setTenantCourseDetail(null);

		return userTenantCourseMap;
	}

}