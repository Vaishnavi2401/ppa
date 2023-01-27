package com.cdac.announcementservice.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "announcement_log")
public class AnnouncementLog implements Serializable{


	private static final long serialVersionUID = -4973618612656401186L;
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  @Column(name = "sno")
	  private int sno;
	  
	  @Column(name = "announcement_id")
	  private int id;
	  
	
	 
	  @Column(name = "announcement_title")
	  private String title;
	  
	  @Column(name = "announcement_body")
	  private String body;
	  
	  @Column(name = "published_from")
	  private Date publihFrom;
	  
	  @Column(name = "published_upto")
	  private Date publishUpto;
	  
	  @Column(name = "update_time", insertable = false)
	  @CreatedDate
	  private Date updatedAt;
	  
	  @Column(name = "updated_by")
	  private String updatedBy;
	  
	  


}
