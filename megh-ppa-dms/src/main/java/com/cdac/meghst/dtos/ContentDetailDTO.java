package com.cdac.meghst.dtos;

import java.sql.Timestamp;

import lombok.Data;


@Data
public class ContentDetailDTO {
	private int contentId;
	private int contentDuration;
	private String contentName;
	private String contentType;
	//private int itemId;
	private Timestamp lastModifiedDate;
	private String lastUpdatedBy;
	private String previewUrl;
	private Timestamp publishDate;
	private String publishStatus;
	private String shareUrl;
	private String streamingStatus;
	private Timestamp uploadDate;
	private String userId;
	//private double versionNo;
}
