package com.cdac.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.persistence.Entity;
import java.sql.Timestamp;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "certificate_id")
    private int certificateId;
        
    @Column(name="course_id")
    private int courseId;
    
    public int getCourse_id() {
		return courseId;
	}
	public void setCourse_id(int course_id) {
		this.courseId = course_id;
	}
	//@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn (name = "template_id", referencedColumnName = "template_id")
    //private Template template;
    @Pattern(regexp="^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$",message="userid  pattern not matching ")
    @Column(name="user_id")
    private String userId;
    @Column(name="certificate_file_path")
    private String certificateFilePath; 
    @Column(name="certificate_hash")
    private String certificateHash; 
    @Column(name="creation_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    private Timestamp creationDate;
    @Column(name="mail_sent")
    private Boolean mailSent; 
    @Column(name="txn_id")
    private int txnId;

	public int getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
	}
	/*public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}*/
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCertificateFilePath() {
		return certificateFilePath;
	}
	public void setCertificateFilePath(String certificateFilePath) {
		this.certificateFilePath = certificateFilePath;
	}
	public String getCertificateHash() {
		return certificateHash;
	}
	public void setCertificateHash(String certificateHash) {
		this.certificateHash = certificateHash;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public Boolean getMailSent() {
		return mailSent;
	}
	public void setMailSent(Boolean mailSent) {
		this.mailSent = mailSent;
	}
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	} 
      
}