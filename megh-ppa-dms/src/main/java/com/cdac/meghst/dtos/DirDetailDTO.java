package com.cdac.meghst.dtos;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cdac.meghst.models.ContentDetail;

import lombok.Data;

@Data
public class DirDetailDTO {
	private String dirChildId;
	private String dirName;
	private String dirParentId;
	private String lastModifiedBy;
	private Timestamp lastModifiedDate;
	private List<ContentDetail> contentDetails;
}
