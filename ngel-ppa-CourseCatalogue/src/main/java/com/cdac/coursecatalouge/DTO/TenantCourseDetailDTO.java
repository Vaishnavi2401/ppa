package com.cdac.coursecatalouge.DTO;

import java.sql.Timestamp;
import java.util.List;

import com.cdac.coursecatalouge.models.TenantCourseDetailPK;

import lombok.Data;

@Data
public class TenantCourseDetailDTO {

	private TenantCourseDetailPKDTO id;

	private String category;

	private int categoryId;

	private Timestamp commencementDate;

	private Timestamp courseClosingDate;

	private String courseDescription;

	private int course_Fee;
	
	private int duration;

	private String courseName;

	private String course_Type;

	private Timestamp enrollEdate;

	private Timestamp enrollSdate;

	private String imageUrl;

	private Timestamp publishDate;

	private String status;
	
	int userCount;
	

}
