package com.cdac.courseorg.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the course_master database table.
 * 
 */
@Entity
@Table(name = "course_master")
@NamedQuery(name = "CourseMaster.findAll", query = "SELECT c FROM CourseMaster c")

public class CourseMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private int courseId;

	@Column(name = "category_id")
	private int categoryId;

	@Column(name = "course_fee")
	private int courseFee;

	@Column(name = "course_name")
	private String courseName;

	@Lob
	@Column(name = "course_structure_json")
	private String courseStructureJson;

	@Column(name = "course_type")
	private String courseType;

	@Column(name = "DIR_CHILD_ID")

	private String dirChildId;

	private int duration;

	@Column(name = "course_access_type")
	private String course_access_type;

	@Lob
	@Column(name = "general_details")
	private String generalDetails;

	@Column(name = "is_scorm_compliant")

	private int isScormCompliant;

	@Lob
	private String prerequisite;

	private String status;

	@Lob
	@Column(name = "user_id")
	private String userId;

	@Lob
	@Column(name = "course_objective")
	private String objective;

	@Lob
	@Column(name = "inst_profile")
	private String inst_profile;

	@Column(name = "fee_discount")
	private int fee_discount;


	public CourseMaster() {
	}

	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	public String getCourseStructureJson() {
		return this.courseStructureJson;
	}

	public void setCourseStructureJson(String courseStructureJson) {
		this.courseStructureJson = courseStructureJson;
	}

	public String getCourseType() {
		return this.courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getDirChildId() {
		return this.dirChildId;
	}

	public void setDirChildId(String dirChildId) {
		this.dirChildId = dirChildId;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}


	public String getCourse_access_type() {
		return course_access_type;
	}

	public void setCourse_access_type(String course_access_type) {
		this.course_access_type = course_access_type;
	}


	public String getGeneralDetails() {
		return this.generalDetails;
	}

	public void setGeneralDetails(String generalDetails) {
		this.generalDetails = generalDetails;
	}

	public int getIsScormCompliant() {
		return this.isScormCompliant;
	}

	public void setIsScormCompliant(int isScormCompliant) {
		this.isScormCompliant = isScormCompliant;
	}

	public String getPrerequisite() {
		return this.prerequisite;
	}

	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getInst_profile() {
		return inst_profile;
	}

	public void setInst_profile(String inst_profile) {
		this.inst_profile = inst_profile;
	}

	public int getFee_discount() {
		return fee_discount;
	}

	public void setFee_discount(int fee_discount) {
		this.fee_discount = fee_discount;
	}


}