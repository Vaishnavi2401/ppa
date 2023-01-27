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
@Table(name = "image_master")
public class Imagemaster {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "template_image_id")
    private int templateImageId;
  
    @Column(name="template_type")
    private String templateType;
    @Column(name="required_alignment_ids")
    private String requiredAlignmentIds;
    @Column(name="background_image")
    private String backgroundImage;
	public int getTemplateImageId() {
		return templateImageId;
	}
	public void setTemplateImageId(int templateImageId) {
		this.templateImageId = templateImageId;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getRequiredAlignmentIds() {
		return requiredAlignmentIds;
	}
	public void setRequiredAlignmentIds(String requiredAlignmentIds) {
		this.requiredAlignmentIds = requiredAlignmentIds;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	        
}