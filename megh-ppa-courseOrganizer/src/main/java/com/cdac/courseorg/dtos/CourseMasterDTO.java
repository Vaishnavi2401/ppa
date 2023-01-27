package com.cdac.courseorg.dtos;

import lombok.Data;

@Data
public class CourseMasterDTO {
	private int courseId;
	private int categoryId;
	private int course_Fee;
	private String courseName;
	private String course_Type;
	private int duration;
	private String course_access_type;
	private String generalDetails;
	private int isScormCompliant;
	private String prerequisite;
	private String objective;
	private String userId;
	private String inst_profile;
	private int fee_discount;
	private int timeDiff;

}
