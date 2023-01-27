package com.cdac.courseorg.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * The persistent class for the content_details database table.
 * 
 */
@Entity
@Table(name = "content_details")
@NamedQuery(name = "ContentDetail.findAll", query = "SELECT c FROM ContentDetail c")

public class ContentDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_id")
	private int contentId;

	@Column(name = "category_type")

	private String categoryType;

	private String cname;


	@Column(name = "content_type")
	private String contentType;

	@Column(name = "course_id")

	private String courseId;

	@Lob
	private String description;

	@Lob
	private String filepath;

	@Column(name = "item_id")
	private String itemId;

	@Column(name = "topic_duration")
	private int topicDuration;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "p_content_id")
	private int pContentId;

	@Column(name = "publish_date")

	private Timestamp publishDate;

	public ContentDetail() {
	}

	public int getContentId() {
		return this.contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getCategoryType() {
		return this.categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getTopicDuration() {
		return this.topicDuration;
	}

	public void setTopicDuration(int topicDuration) {
		this.topicDuration = topicDuration;
	}


	public int getpContentId() {
		return pContentId;
	}

	public void setpContentId(int pContentId) {
		this.pContentId = pContentId;
	}


	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

}