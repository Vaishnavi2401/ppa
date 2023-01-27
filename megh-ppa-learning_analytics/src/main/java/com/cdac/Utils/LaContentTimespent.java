package com.cdac.Utils;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import java.sql.Timestamp;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Validated
public class LaContentTimespent {
    
    public String resTitle;
    public String spentTime;
   
	public String getResTitle() {
		return resTitle;
	}
	public void setResTitle(String resTitle) {
		this.resTitle = resTitle;
	}
	public String getSpentTime() {
		return spentTime;
	}
	public void setSpentTime(String spentTime) {
		this.spentTime = spentTime;
	}
	

}