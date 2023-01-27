package com.cdac.meghst.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the content_details database table.
 * 
 */
@Entity
@Table(name="content_details")
@NamedQuery(name="ContentDetail.findAll", query="SELECT c FROM ContentDetail c")
public class ContentDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CONTENT_ID")
	private int contentId;

	@Column(name="CONTENT_DURATION")
	private int contentDuration;

	@Column(name="CONTENT_NAME")
	private String contentName;

	@Column(name="CONTENT_TYPE")
	private String contentType;

//	@Column(name="ITEM_ID")
//	private int itemId;

	@Column(name="LAST_MODIFIED_DATE")
	private Timestamp lastModifiedDate;

	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name="PREVIEW_URL")
	private String previewUrl;

	@Column(name="PUBLISH_DATE")
	private Timestamp publishDate;

	@Column(name="PUBLISH_STATUS")
	private String publishStatus;

	@Column(name="SHARE_URL")
	private String shareUrl;

	@Column(name="STREAMING_STATUS")
	private String streamingStatus;

	@Column(name="UPLOAD_DATE")
	private Timestamp uploadDate;

	@Column(name="USER_ID")
	private String userId;

//	@Column(name="VERSION_NO")
//	private double versionNo;

	@Column(name="VIRUS_SCAN_STATUS")
	private int virusScanStatus;

	//bi-directional many-to-one association to DirDetail
	@ManyToOne
	@JoinColumn(name="DIR_CHILD_ID")
	private DirDetail dirDetail;

	//bi-directional many-to-one association to VersionMaster
	@OneToMany(mappedBy="contentDetail")
	private List<VersionMaster> versionMasters;

	public ContentDetail() {
	}

	public int getContentId() {
		return this.contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public int getContentDuration() {
		return this.contentDuration;
	}

	public void setContentDuration(int contentDuration) {
		this.contentDuration = contentDuration;
	}

	public String getContentName() {
		return this.contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

//	public int getItemId() {
//		return this.itemId;
//	}
//
//	public void setItemId(int itemId) {
//		this.itemId = itemId;
//	}

	public Timestamp getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getPreviewUrl() {
		return this.previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public Timestamp getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishStatus() {
		return this.publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getShareUrl() {
		return this.shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getStreamingStatus() {
		return this.streamingStatus;
	}

	public void setStreamingStatus(String streamingStatus) {
		this.streamingStatus = streamingStatus;
	}

	public Timestamp getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

//	public double getVersionNo() {
//		return this.versionNo;
//	}

//	public void setVersionNo(double versionNo) {
//		this.versionNo = versionNo;
//	}

	public int getVirusScanStatus() {
		return this.virusScanStatus;
	}

	public void setVirusScanStatus(int virusScanStatus) {
		this.virusScanStatus = virusScanStatus;
	}

	public DirDetail getDirDetail() {
		return this.dirDetail;
	}

	public void setDirDetail(DirDetail dirDetail) {
		this.dirDetail = dirDetail;
	}

	public List<VersionMaster> getVersionMasters() {
		return this.versionMasters;
	}

	public void setVersionMasters(List<VersionMaster> versionMasters) {
		this.versionMasters = versionMasters;
	}

	public VersionMaster addVersionMaster(VersionMaster versionMaster) {
		getVersionMasters().add(versionMaster);
		versionMaster.setContentDetail(this);

		return versionMaster;
	}

	public VersionMaster removeVersionMaster(VersionMaster versionMaster) {
		getVersionMasters().remove(versionMaster);
		versionMaster.setContentDetail(null);

		return versionMaster;
	}

}