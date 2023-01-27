package com.cdac.coursecatalouge.DTO;

import lombok.Data;

@Data
public class CheckUserStatusDTO {

	boolean courseEnrolled;
	boolean certificateGenerated;
	int instCourseStatus;
}
