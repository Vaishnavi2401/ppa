package com.cdac.Entity;

import javax.persistence.CascadeType;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity
@Validated
@Table(name = "template_tenant")
public class Template_tenant {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "sno")
    private int sno;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "template_id", referencedColumnName = "template_id")
    private Template template;
 
    @Column (name = "course_id")
    private int courseId;

    @Column (name = "tenant_id")
    private int tenantId;
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

        
}