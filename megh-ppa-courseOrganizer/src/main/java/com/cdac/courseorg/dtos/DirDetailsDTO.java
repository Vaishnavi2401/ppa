package com.cdac.courseorg.dtos;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DirDetailsDTO {
	private String dirChildId;
	private Timestamp creationDate;
	private String dirName;
	private String dirParentId;
	private String dirPath;
	private String lastModifiedBy;
	private Timestamp lastModifiedDate;
	private String publishDate;
}
