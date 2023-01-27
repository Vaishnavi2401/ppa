package com.cdac.Entity;

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
@Entity
@Validated
@Table(name = "content_field_master")
public class Contentfieldmaster {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "field_id")
    private int  fieldId;
  
    @Column(name="field_name")
    private String fieldName;

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	        
}