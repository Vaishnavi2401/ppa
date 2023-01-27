package com.cdac.ratingandreview.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the reply_review database table.
 * 
 */
@Entity
@Table(name="reply_review")
@NamedQuery(name="ReplyReview.findAll", query="SELECT r FROM ReplyReview r")
public class ReplyReview implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REPLY_ID")
	private int replyId;

	@Column(name="REPLIED_BY")
	private String repliedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REPLIED_DATE")
	private Date repliedDate;

	@Column(name="REPLY_TEXT")
	private String replyText;

	//bi-directional many-to-one association to ReviewDetail
	@ManyToOne
	@JoinColumn(name="REVIEW_ID")
	private ReviewDetail reviewDetail;


	public ReplyReview() {
	}

	public int getReplyId() {
		return this.replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public String getRepliedBy() {
		return this.repliedBy;
	}

	public void setRepliedBy(String repliedBy) {
		this.repliedBy = repliedBy;
	}


	public Date getRepliedDate() {
		return repliedDate;
	}

	public void setRepliedDate(Date repliedDate) {
		this.repliedDate = repliedDate;
	}

	public String getReplyText() {
		return this.replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public ReviewDetail getReviewDetail() {
		return this.reviewDetail;
	}

	public void setReviewDetail1(ReviewDetail reviewDetail) {
		this.reviewDetail = reviewDetail;
	}


}