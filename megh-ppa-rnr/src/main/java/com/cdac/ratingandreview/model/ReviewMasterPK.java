package com.cdac.ratingandreview.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the review_master database table.
 * 
 */
@Embeddable
public class ReviewMasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="LEARNER_ID")
	private String learnerId;

	@Column(name="RVIEW_ITEM_ID")
	private String rviewItemId;

	public ReviewMasterPK() {
	}
	public String getLearnerId() {
		return this.learnerId;
	}
	public void setLearnerId(String learnerId) {
		this.learnerId = learnerId;
	}
	public String getRviewItemId() {
		return this.rviewItemId;
	}
	public void setRviewItemId(String rviewItemId) {
		this.rviewItemId = rviewItemId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReviewMasterPK)) {
			return false;
		}
		ReviewMasterPK castOther = (ReviewMasterPK)other;
		return 
			this.learnerId.equals(castOther.learnerId)
			&& this.rviewItemId.equals(castOther.rviewItemId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.learnerId.hashCode();
		hash = hash * prime + this.rviewItemId.hashCode();
		
		return hash;
	}
}