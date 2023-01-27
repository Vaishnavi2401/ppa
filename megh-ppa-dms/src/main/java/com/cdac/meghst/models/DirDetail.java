package com.cdac.meghst.models;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the dir_details database table.
 * 
 */
@Entity
@Table(name="dir_details")
//@NamedQuery(name="DirDetail.findAll", query="SELECT d FROM DirDetail d")
public class DirDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIR_CHILD_ID")
	private String dirChildId;

	@Column(name="DIR_NAME")
	private String dirName;
	
	@Column(name="DIR_Path")
	private String dirPath;

	@Column(name="DIR_PARENT_ID")
	private String dirParentId;

	@Column(name="LAST_MODIFIED_BY")
	private String lastModifiedBy;
	
	@Column(name="JSON_DATA") 
	private String jsonData;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_MODIFIED_DATE")
	private Date lastModifiedDate;

	public DirDetail() {
	}

	public String getDirChildId() {
		return this.dirChildId;
	}

	public void setDirChildId(String dirChildId) {
		this.dirChildId = dirChildId;
	}

	public String getDirName() {
		return this.dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	
	public String getDirPath() {
		return this.dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getDirParentId() {
		return this.dirParentId;
	}

	public void setDirParentId(String dirParentId) {
		this.dirParentId = dirParentId;
	}

	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

}