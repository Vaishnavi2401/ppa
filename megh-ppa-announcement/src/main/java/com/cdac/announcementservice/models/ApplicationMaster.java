package com.cdac.announcementservice.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "application_master")
public class ApplicationMaster implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "application_id")
	@ApiModelProperty(notes = "Application Id which is autoincriment")
	private int id;

	@Column(name = "application_hash_identifier")
	@ApiModelProperty(notes = "Tenant identifier")
	private int appHashIdentifier;

	@Column(name = "	application_name")
	@ApiModelProperty(notes = "Tenant Name")
	private String tenantName;

	@Column(name = "application_description")
	@ApiModelProperty(notes = "Tenant Name")
	private String tenantDescription;;
}
