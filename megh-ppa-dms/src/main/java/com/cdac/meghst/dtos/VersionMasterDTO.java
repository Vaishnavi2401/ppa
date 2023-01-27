package com.cdac.meghst.dtos;

import java.sql.Timestamp;
import com.cdac.meghst.models.ContentDetail;

import lombok.Data;

@Data
public class VersionMasterDTO {
	private int versionId;
	private Timestamp lastModifiedDate;
	private String versionChanges;
	private ContentDetail contentDetail;
	private double versionNo;
}
