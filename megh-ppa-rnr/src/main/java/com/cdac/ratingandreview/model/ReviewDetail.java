package com.cdac.ratingandreview.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the review_details database table.
 * 
 */
@Entity
@Table(name="review_details")
@NamedQuery(name="ReviewDetail.findAll", query="SELECT r FROM ReviewDetail r")
public class ReviewDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="REVIEW_ID")
	private int reviewId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATION_TIME")
	private Date creationTime;

	@Column(name="RATING")
	private int rating;

	@Column(name="REVIEW_STATUS")
	private String reviewStatus;

	@Column(name="REVIEW_TEXT")
	private String reviewText;
	
	@Column(name="TENANT_ID")
	private int tenantId;

	@Column(name="REVIEW_TYPE")
	private String reviewType;

	@Column(name="SENTIMENT_ANALYSIS")
	private String sentimentAnalysis;

	@JsonIgnore
	//bi-directional many-to-one association to ReplyReview
	@OneToMany(mappedBy="reviewDetail")
	private List<ReplyReview> replyReviews;

	@JsonIgnore
	//bi-directional many-to-one association to ReviewMaster
	@OneToMany(mappedBy="reviewDetail")
	private List<ReviewMaster> reviewMasters;

	@JsonIgnore
	//bi-directional one-to-one association to SentimentAnalysi
	@OneToOne(mappedBy="reviewDetail")
	private SentimentAnalysi sentimentAnalysi;

	public ReviewDetail() {
	}

	public int getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReviewStatus() {
		return this.reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getReviewText() {
		return this.reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public String getReviewType() {
		return this.reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	
	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public String getSentimentAnalysis() {
		return this.sentimentAnalysis;
	}

	public void setSentimentAnalysis(String sentimentAnalysis) {
		this.sentimentAnalysis = sentimentAnalysis;
	}

	public List<ReplyReview> getReplyReviews() {
		return this.replyReviews;
	}

	public void setReplyReviews(List<ReplyReview> replyReviews) {
		this.replyReviews = replyReviews;
	}

	public ReplyReview addReplyReviews(ReplyReview replyReviews) {
		getReplyReviews().add(replyReviews);
		replyReviews.setReviewDetail1(this);

		return replyReviews;
	}

	public ReplyReview removeReplyReviews(ReplyReview replyReviews) {
		getReplyReviews().remove(replyReviews);
		replyReviews.setReviewDetail1(null);

		return replyReviews;
	}

	

	public List<ReviewMaster> getReviewMasters() {
		return this.reviewMasters;
	}

	public void setReviewMasters(List<ReviewMaster> reviewMasters) {
		this.reviewMasters = reviewMasters;
	}

	public ReviewMaster addReviewMasters(ReviewMaster reviewMasters) {
		getReviewMasters().add(reviewMasters);
		reviewMasters.setReviewDetail(this);

		return reviewMasters;
	}

	public ReviewMaster removeReviewMasters(ReviewMaster reviewMasters) {
		getReviewMasters().remove(reviewMasters);
		reviewMasters.setReviewDetail(null);

		return reviewMasters;
	}

	
	public SentimentAnalysi getSentimentAnalysi() {
		return this.sentimentAnalysi;
	}

	public void setSentimentAnalysi(SentimentAnalysi sentimentAnalysi) {
		this.sentimentAnalysi = sentimentAnalysi;
	}

}