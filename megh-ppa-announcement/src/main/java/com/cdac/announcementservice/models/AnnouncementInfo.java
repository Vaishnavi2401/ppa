package com.cdac.announcementservice.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Embeddable
public class AnnouncementInfo implements Serializable{
	
	@Column(name = "announcement_id")
	@ApiModelProperty(notes = "Announcemnt Id")
    private int announcementId;
 
    @Column(name = "user_id")
    @ApiModelProperty(notes = "User Id")
    private String userId;

}
