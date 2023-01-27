package com.cdac.courseorg.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class PublishedCoursDTO {
	private String id;
	private byte active;
	private String courseId;
	private Date creationTime;
	private String location;
	private String size;
	private byte start;
	private byte toc;
	private String userId;
	private String userWorkspaceId;
}
