package com.cdac.courseorg.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the published_courses database table.
 * 
 */
@Entity
@Table(name="published_courses")
@NamedQuery(name="PublishedCours.findAll", query="SELECT p FROM PublishedCours p")
public class PublishedCours implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private byte active;

	@Column(name="course_id")
	private String courseId;

	@Temporal(TemporalType.DATE)
	private Date creationTime;

	@Lob
	private String location;

	@Lob
	private String size;

	private byte start;

	private byte toc;

	@Column(name="user_id")
	private String userId;

	@Lob
	private String userWorkspaceId;

	public PublishedCours() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public byte getStart() {
		return this.start;
	}

	public void setStart(byte start) {
		this.start = start;
	}

	public byte getToc() {
		return this.toc;
	}

	public void setToc(byte toc) {
		this.toc = toc;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserWorkspaceId() {
		return this.userWorkspaceId;
	}

	public void setUserWorkspaceId(String userWorkspaceId) {
		this.userWorkspaceId = userWorkspaceId;
	}

}