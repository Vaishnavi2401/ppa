package com.cdac.Entity;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Entity;
import java.sql.Timestamp;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Validated
@Table(name = "la_user_action")
public class LaUserAction {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "sno")
    private int sno;

    @Column(name="site_id")
    private int site_id;
    @Pattern(regexp="^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$",message="userid  pattern not matching ")
    @Column(name="user_id")
    private String userId;
    
    @Column(name="visit_last_action_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp visit_last_action_time;
    @Column(name="Logout")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp Logout;		
    @Column(name="ip_address")
    private String ip_address;
    @Column(name="action")
    private String action;
    @Column(name="config_os")
    private String config_os;
    @Column(name="config_resolution")
    private String config_resolution;
    @Column(name="config_browser")
    private String config_browser;

    @Column(name="session_id")
    @Pattern(regexp="^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$",message="sessionid  pattern not matching ")
    private String sessionId;
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}

	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getVisit_last_action_time() {
		return visit_last_action_time;
	}
	public void setVisit_last_action_time(Timestamp visit_last_action_time) {
		this.visit_last_action_time = visit_last_action_time;
	}
	
	public Timestamp getLogout() {
		return Logout;
	}
	public void setLogout(Timestamp logout) {
		Logout = logout;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getConfig_os() {
		return config_os;
	}
	public void setConfig_os(String config_os) {
		this.config_os = config_os;
	}
	public String getConfig_resolution() {
		return config_resolution;
	}
	public void setConfig_resolution(String config_resolution) {
		this.config_resolution = config_resolution;
	}
	public String getConfig_browser() {
		return config_browser;
	}
	public void setConfig_browser(String config_browser) {
		this.config_browser = config_browser;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
    
}