package com.cdac.courseorg.dtos;

import lombok.Data;

@Data
public class IteminfoDTO {
	private int activityID;
	private byte abandon;
	private String attemptAbsoluteDurationLimit;
	private String completionThreshold;
	private String courseId;
	private String dataFromLMS;
	private byte exit1;
	private byte exitAll;
	private String itemIdentifier;
	private String launch;
	private String minNormalizedMeasure;
	private byte next;
	private String organizationIdentifier;
	private String parameterString;
	private byte previous;
	private String resourceIdentifier;
	private byte suspend;
	private String timeLimitAction;
	private String title;
	private String type;
}
