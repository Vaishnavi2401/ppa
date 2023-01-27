package com.cdac.courseorg.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dir_details database table.
 * 
 */
@Entity
@Table(name="dir_details")
@NamedQuery(name="DirDetail.findAll", query="SELECT d FROM DirDetail d order by DIR_PARENT_ID")

public class DirDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIR_CHILD_ID")
	private String dirChildId;

	@Column(name="CREATION_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp creationDate;

	@Column(name="DIR_NAME")
	private String dirName;

	@Column(name="DIR_PARENT_ID")
	private String dirParentId;

	@Column(name="DIR_PATH")
	private String dirPath;

	@Column(name="LAST_MODIFIED_BY")
	private String lastModifiedBy;

	@Column(name="LAST_MODIFIED_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp lastModifiedDate;

	@Column(name="PUBLISH_DATE")
	private Timestamp publishDate;

	public DirDetail() {
	}

	public String getDirChildId() {
		return this.dirChildId;
	}

	public void setDirChildId(String dirChildId) {
		this.dirChildId = dirChildId;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getDirName() {
		return this.dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getDirParentId() {
		return this.dirParentId;
	}

	public void setDirParentId(String dirParentId) {
		this.dirParentId = dirParentId;
	}

	public String getDirPath() {
		return this.dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Timestamp getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

}