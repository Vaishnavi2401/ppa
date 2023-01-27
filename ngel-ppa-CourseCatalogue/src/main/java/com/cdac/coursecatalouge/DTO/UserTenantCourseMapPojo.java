package com.cdac.coursecatalouge.DTO;

import java.sql.Timestamp;
import java.util.List;

import com.cdac.coursecatalouge.models.TenantCourseDetail;

import lombok.Data;

@Data
public class UserTenantCourseMapPojo {

	private String userId;

	private int courseId;

	private int tenantId;


}
