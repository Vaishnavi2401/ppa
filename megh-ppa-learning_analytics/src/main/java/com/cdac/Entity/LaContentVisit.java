package com.cdac.Entity;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import javax.persistence.Entity;
import java.sql.Timestamp;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Validated
@Table(name = "la_content_visits")
public class LaContentVisit {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "SNO")
    private int sno;
    @Pattern(regexp="^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$",message="userid  pattern not matching ")
    @Column(name="user_id")
    private String userId;
    @Pattern(regexp="^([1-9][0-9]{0,2}|1000)$",message="courseid  pattern not matching ")
    @Column(name="course_id")
    private String courseId;
    @Column(name="res_id")
    private String resId;
    @Column(name="res_title")
    private String resTitle;
    @Column(name="in_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp inTime;
    @Column(name="out_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp outTime;		
    @Pattern(regexp="^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$",message="sessionid  pattern not matching ")
    @Column(name="session_id")
    private String sessionId;
    @Column(name="filetype")
    private String filetype;
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResTitle() {
		return resTitle;
	}
	public void setResTitle(String resTitle) {
		this.resTitle = resTitle;
	}
	public Timestamp getInTime() {
		return inTime;
	}
	public void setInTime(Timestamp inTime) {
		this.inTime = inTime;
	}
	public Timestamp getOutTime() {
		return outTime;
	}
	public void setOutTime(Timestamp outTime) {
		this.outTime = outTime;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
   
}