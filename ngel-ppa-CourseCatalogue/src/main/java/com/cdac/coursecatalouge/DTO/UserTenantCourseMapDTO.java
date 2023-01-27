package com.cdac.coursecatalouge.DTO;

import java.sql.Timestamp;
import java.util.List;

import com.cdac.coursecatalouge.models.TenantCourseDetail;

import lombok.Data;

@Data
public class UserTenantCourseMapDTO {

	private UserTenantCourseMapPKDTO id;

	private String certificateId;

	private Timestamp certificateIssuedDate;

	private Timestamp completionDate;

	private Timestamp courseEndDate;

	private String courseProgress;

	private Timestamp courseStartDate;

	private byte suspendAll;

	private TenantCourseDetailDTO courseDetails;

	List<LearnerMasterDTO> instructor;

}
