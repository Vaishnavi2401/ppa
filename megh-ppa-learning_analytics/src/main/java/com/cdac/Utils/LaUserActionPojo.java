package com.cdac.Utils;

import java.sql.Timestamp;
import java.util.Date;

public class LaUserActionPojo {
    
    private String userId;
   
    private String visit_last_action_time;
   
    private String Logout;		
    
    private String ip_address;
    
    private String action;
    
    private String config_os;
    
    private String config_resolution;
    
    private String config_browser;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getVisit_last_action_time() {
		return visit_last_action_time;
	}

	public void setVisit_last_action_time(String visit_last_action_time) {
		this.visit_last_action_time = visit_last_action_time;
	}

	public String getLogout() {
		return Logout;
	}

	public void setLogout(String logout) {
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

    
}