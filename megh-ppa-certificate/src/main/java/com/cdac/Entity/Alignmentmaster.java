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
@Table(name = "alignment_master")
public class Alignmentmaster {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "alignment_id")
    private int alignmentId;
  
    @Column(name="alignment_name")
    private String alignmentName;
    @OneToMany(mappedBy="alignmentmaster")
	private List<Subtemplatemap> subtemplatemap;
	public int getAlignmentId() {
		return alignmentId;
	}

	public void setAlignmentId(int alignmentId) {
		this.alignmentId = alignmentId;
	}

	public String getAlignmentName() {
		return alignmentName;
	}

	public void setAlignmentName(String alignmentName) {
		this.alignmentName = alignmentName;
	}
         
}