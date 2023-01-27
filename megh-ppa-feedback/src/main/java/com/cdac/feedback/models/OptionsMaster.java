package com.cdac.feedback.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the options_master database table.
 * 
 */
@Entity
@Table(name="options_master")
@NamedQuery(name="OptionsMaster.findAll", query="SELECT o FROM OptionsMaster o")
public class OptionsMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="option_id")
	private int optionId;

	@Column(name="option_text")
	private String optionText;

	//bi-directional many-to-one association to QuestionMaster
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="question_id")
	private QuestionMaster questionMaster;

	public OptionsMaster() {
		/* Empty Constructor*/
	}

	public int getOptionId() {
		return this.optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public String getOptionText() {
		return this.optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public QuestionMaster getQuestionMaster() {
		return this.questionMaster;
	}

	public void setQuestionMaster(QuestionMaster questionMaster) {
		this.questionMaster = questionMaster;
	}

}