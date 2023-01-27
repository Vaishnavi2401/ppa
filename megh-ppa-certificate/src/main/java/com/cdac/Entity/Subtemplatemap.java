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
@Entity
@Validated
@Table(name = "subtemplate_map")
public class Subtemplatemap {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "sno")
    private int sno;
  
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "template_id", referencedColumnName = "template_id")
    private Template template;
 
    @Column(name="logo_sign")
    private String logoSign;
    @Column(name="logo_sign_name")
    private String logoSignName;
   
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "alignment_id", referencedColumnName = "alignment_id")
    private Alignmentmaster alignmentmaster;
 
   
    @Column(name="type")
    private String type;
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
	public String getLogoSign() {
		return logoSign;
	}
	public void setLogoSign(String logoSign) {
		this.logoSign = logoSign;
	}
	public String getLogoSignName() {
		return logoSignName;
	}
	public void setLogoSignName(String logoSignName) {
		this.logoSignName = logoSignName;
	}

	public Alignmentmaster getAlignmentmaster() {
		return alignmentmaster;
	}
	public void setAlignmentmaster(Alignmentmaster alignmentmaster) {
		this.alignmentmaster = alignmentmaster;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
		        
}