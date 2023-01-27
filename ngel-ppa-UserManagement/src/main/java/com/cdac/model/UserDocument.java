package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_documents database table.
 * 
 */
@Entity
@Table(name="user_documents")
@NamedQuery(name="UserDocument.findAll", query="SELECT u FROM UserDocument u")
public class UserDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String emailid;

	private String idcard;

	private String photograph;

	public UserDocument() {
	}

	public String getEmailid() {
		return this.emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPhotograph() {
		return this.photograph;
	}

	public void setPhotograph(String photograph) {
		this.photograph = photograph;
	}

}