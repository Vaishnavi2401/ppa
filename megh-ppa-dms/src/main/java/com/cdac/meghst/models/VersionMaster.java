package com.cdac.meghst.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the version_master database table.
 * 
 */
@Entity
@Table(name = "version_master")
@NamedQuery(name = "VersionMaster.findAll", query = "SELECT v FROM VersionMaster v")
public class VersionMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "VERSION_ID")
	private int versionId;

	@Column(name = "LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;

	@Column(name = "VERSION_CHANGES")
	private String versionChanges;

	@Column(name = "VERSION_NO")
	private double versionNo;

	// bi-directional many-to-one association to ContentDetail
	@ManyToOne
	@JoinColumn(name = "CONTENT_ID")
	private ContentDetail contentDetail;

	public VersionMaster() {
	}

	public int getVersionId() {
		return this.versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public Timestamp getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Object getVersionChanges() {
		return this.versionChanges;
	}

	public void setVersionChanges(String versionChanges) {
		this.versionChanges = versionChanges;
	}

	public ContentDetail getContentDetail() {
		return this.contentDetail;
	}

	public void setContentDetail(ContentDetail contentDetail) {
		this.contentDetail = contentDetail;
	}

	public double getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(double versionNo) {
		this.versionNo = versionNo;
	}

}