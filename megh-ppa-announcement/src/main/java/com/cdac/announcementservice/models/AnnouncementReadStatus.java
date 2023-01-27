package com.cdac.announcementservice.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "announcement_read_status")

public class AnnouncementReadStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7102814282193956889L;

	@EmbeddedId
	private AnnouncementInfo id;
	@Column(name = "read_time", insertable = false)
	@CreatedDate
	private Date readTime;

}
