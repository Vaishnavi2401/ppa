package com.cdac.announcementservice.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "announcements_master")

public class Announcement implements Serializable {
  






@Override
	public String toString() {
		return "Announcement [id=" + id + ", type=" + type + ", title=" + title + ", body=" + body + ", publihFrom="
				+ publihFrom + ", publishUpto=" + publishUpto + ", createdAt=" + createdAt + ", createdBy=" + createdBy
				+ ", courseId=" + courseId + ", readStatus=" + readStatus + "]";
	}



private static final long serialVersionUID = 6353601318078248404L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)

  @Column(name = "announcement_id")
  @ApiModelProperty(notes = "Announcemnt Id which is autoincriment")
  private int id;
  
  @Column(name = "announcement_type")
  @ApiModelProperty(notes = "announcement type describes the announcement is related to general or course. 1 means general and 2 means course")
  private int type;
 
  @Column(name = "announcement_title")
  @ApiModelProperty(notes = "Announcemnt Title")
  private String title;
  
  @Column(name = "announcement_body")
  @ApiModelProperty(notes = "Announcemnt Body")
  private String body;
  
  @Column(name = "publish_from")
  @ApiModelProperty(notes = "Publish from")
  private Date publihFrom;
  
  @Column(name = "publish_upto")
  @ApiModelProperty(notes = "Publish upto")
  private Date publishUpto;
  
  @Column(name = "creation_time", insertable = false)
  @ApiModelProperty(notes = "Describes the announcement creation time")
  @CreatedDate
  private Date createdAt;
  
  @Column(name = "created_by")
  @ApiModelProperty(notes = "Describes who created the announcement")
  private String createdBy;
  
  @Column(name = "course_id")
  @ApiModelProperty(notes = "course id")
  private String courseId;
  
  @Transient
  @ApiModelProperty(notes = "Announcemnt read or not")
  private String readStatus;
  
}
