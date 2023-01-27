package com.cdac.courseorg.dtos;

import lombok.Data;

@Data
public class UpdatedCourseMasterDTO {
	int courseId;
	String categoryName;
	String courseName;
	int duration;
	String generalDetails;
	String prerequisite;
	String courseType;
	Integer courseFee;
	String publishDate;
	String enrollStartDate;
	String enrollEndDate;
	String commenceDate;
	String closingDate;
	String imageUrl;
}
