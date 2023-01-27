package com.cdac.coursecatalouge.DTO;

import lombok.Data;

@Data
public class UserTenantCourseMapPKDTO {

	private String userId;

	private int courseId;

	private int tenantId;

	private int roleId;
}

