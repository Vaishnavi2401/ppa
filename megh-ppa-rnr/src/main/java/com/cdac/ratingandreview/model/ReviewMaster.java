package com.cdac.ratingandreview.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the review_master database table.
 * 
 */
@Entity
@Table(name="review_master")
@NamedQuery(name="ReviewMaster.findAll", query="SELECT r FROM ReviewMaster r")
public class ReviewMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReviewMasterPK id;

	//bi-directional many-to-one association to ReviewDetail
	@ManyToOne
	@JoinColumn(name="REVIEW_ID")
	private ReviewDetail reviewDetail;

	
	public ReviewMaster() {
	}

	public ReviewMasterPK getId() {
		return this.id;
	}

	public void setId(ReviewMasterPK id) {
		this.id = id;
	}

	public ReviewDetail getReviewDetail() {
		return this.reviewDetail;
	}

	public void setReviewDetail(ReviewDetail reviewDetail) {
		this.reviewDetail = reviewDetail;
	}

}