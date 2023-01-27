package com.cdac.courseorg.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the course_schedule database table.
 * 
 */
@Entity
@Table(name = "course_schedule")
@NamedQuery(name = "CourseSchedule.findAll", query = "SELECT c FROM CourseSchedule c")

public class CourseSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "course_id")
	private int courseId;

	@Column(name = "commencement_date")
	private Timestamp commencementDate;

	@Column(name = "course_closing_date")
	private Timestamp courseClosingDate;

	@Column(name = "enroll_edate")
	private Timestamp enrollEdate;

	@Column(name = "enroll_sdate")
	private Timestamp enrollSdate;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "publish_date")
	private Timestamp publishDate;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "video")
	private String video;

	@Column(name = "banner")
	private String banner;

	// bi-directional one-to-one association to CourseMaster
	@OneToOne
	@JoinColumn(name = "course_id")
	private CourseMaster courseMaster;

	public CourseSchedule() {
	}

	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public CourseMaster getCourseMaster() {
		return this.courseMaster;
	}

	public void setCourseMaster(CourseMaster courseMaster) {
		this.courseMaster = courseMaster;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}


}