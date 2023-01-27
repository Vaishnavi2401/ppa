package com.cdac.courseorg.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the iteminfo database table.
 * 
 */
@Entity
@NamedQuery(name="Iteminfo.findAll", query="SELECT i FROM Iteminfo i")
public class Iteminfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int activityID;

	private byte abandon;

	private String attemptAbsoluteDurationLimit;

	private String completionThreshold;

	@Column(name="course_id")
	private String courseId;

	@Lob
	private String dataFromLMS;

	private byte exit1;

	private byte exitAll;

	private String itemIdentifier;

	@Lob
	private String launch;

	private String minNormalizedMeasure;

	private byte next;

	private String organizationIdentifier;

	@Lob
	private String parameterString;

	private byte previous;

	private String resourceIdentifier;

	private byte suspend;

	private String timeLimitAction;

	private String title;

	private String type;

	public Iteminfo() {
	}

	public int getActivityID() {
		return this.activityID;
	}

	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}

	public byte getAbandon() {
		return this.abandon;
	}

	public void setAbandon(byte abandon) {
		this.abandon = abandon;
	}

	public String getAttemptAbsoluteDurationLimit() {
		return this.attemptAbsoluteDurationLimit;
	}

	public void setAttemptAbsoluteDurationLimit(String attemptAbsoluteDurationLimit) {
		this.attemptAbsoluteDurationLimit = attemptAbsoluteDurationLimit;
	}

	public String getCompletionThreshold() {
		return this.completionThreshold;
	}

	public void setCompletionThreshold(String completionThreshold) {
		this.completionThreshold = completionThreshold;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getDataFromLMS() {
		return this.dataFromLMS;
	}

	public void setDataFromLMS(String dataFromLMS) {
		this.dataFromLMS = dataFromLMS;
	}

	public byte getExit1() {
		return this.exit1;
	}

	public void setExit1(byte exit1) {
		this.exit1 = exit1;
	}

	public byte getExitAll() {
		return this.exitAll;
	}

	public void setExitAll(byte exitAll) {
		this.exitAll = exitAll;
	}

	public String getItemIdentifier() {
		return this.itemIdentifier;
	}

	public void setItemIdentifier(String itemIdentifier) {
		this.itemIdentifier = itemIdentifier;
	}

	public String getLaunch() {
		return this.launch;
	}

	public void setLaunch(String launch) {
		this.launch = launch;
	}

	public String getMinNormalizedMeasure() {
		return this.minNormalizedMeasure;
	}

	public void setMinNormalizedMeasure(String minNormalizedMeasure) {
		this.minNormalizedMeasure = minNormalizedMeasure;
	}

	public byte getNext() {
		return this.next;
	}

	public void setNext(byte next) {
		this.next = next;
	}

	public String getOrganizationIdentifier() {
		return this.organizationIdentifier;
	}

	public void setOrganizationIdentifier(String organizationIdentifier) {
		this.organizationIdentifier = organizationIdentifier;
	}

	public String getParameterString() {
		return this.parameterString;
	}

	public void setParameterString(String parameterString) {
		this.parameterString = parameterString;
	}

	public byte getPrevious() {
		return this.previous;
	}

	public void setPrevious(byte previous) {
		this.previous = previous;
	}

	public String getResourceIdentifier() {
		return this.resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	public byte getSuspend() {
		return this.suspend;
	}

	public void setSuspend(byte suspend) {
		this.suspend = suspend;
	}

	public String getTimeLimitAction() {
		return this.timeLimitAction;
	}

	public void setTimeLimitAction(String timeLimitAction) {
		this.timeLimitAction = timeLimitAction;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}