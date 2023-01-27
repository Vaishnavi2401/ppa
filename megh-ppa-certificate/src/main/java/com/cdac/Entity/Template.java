package com.cdac.Entity;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
@Validated
@Table(name = "template")
public class Template {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "template_id")
    private int templateId;
  
    @Column(name="template_title")
    private String templateTitle;
    @Column(name="template_content")
    private String templateContent;
    @Column(name="background_image_path")
    private String backgroundImagePath;
    @Column(name="template_type")
    private String templateType;
    @Column(name="description")
    private String description;
    @Column(name="qrcode_required")
    private Boolean qrcodeRequired;
    @OneToMany(mappedBy="template")
  	private List<Subtemplatemap> subtemplatemap;
    @OneToMany(mappedBy="template")
  	private List<Template_tenant> templatetenant;

    
    public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public String getTemplateTitle() {
		return templateTitle;
	}
	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}
	public void setBackgroundImagePath(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getQrcodeRequired() {
		return qrcodeRequired;
	}
	public void setQrcodeRequired(Boolean qrcodeRequired) {
		this.qrcodeRequired = qrcodeRequired;
	}
      
}