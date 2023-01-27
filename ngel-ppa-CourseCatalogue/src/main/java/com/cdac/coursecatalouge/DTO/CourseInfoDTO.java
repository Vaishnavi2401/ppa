package com.cdac.coursecatalouge.DTO;

import java.util.List;

import lombok.Data;

@Data
public class CourseInfoDTO {

	int courseId;
	String courseName;
	int catId;
	String catName;
	String courseImage;
	String courseType;
	String courseDescription;
	int duration;
	double fees;
	String publishDate;
	String closingDate;
	String courseEDate;
	String courseSDate;
	List<LearnerMasterDTO> instructor;
	int userCount;
	Integer tenantId;
	int roleId;
	String userId;
	String status;

	public CourseInfoDTO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CourseInfoDTO [courseId=" + courseId + ", courseName=" + courseName + ", catId=" + catId + ", catName="
				+ catName + ", courseImage=" + courseImage + ", courseType=" + courseType + ", courseDescription="
				+ courseDescription + ", duration=" + duration + ", fees=" + fees + ", publishDate=" + publishDate
				+ ", closingDate=" + closingDate + ", author=" + instructor + ", userCount=" + userCount + ", tenantId="
				+ tenantId + "]";
	}

}
