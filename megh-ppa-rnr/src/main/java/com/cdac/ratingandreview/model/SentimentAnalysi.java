package com.cdac.ratingandreview.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sentiment_analysis database table.
 * 
 */
@Entity
@Table(name="sentiment_analysis")
@NamedQuery(name="SentimentAnalysi.findAll", query="SELECT s FROM SentimentAnalysi s")
public class SentimentAnalysi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REVIEW_ID")
	private int reviewId;

	@Column(name="DATE")
	private Timestamp date;

	@Column(name="NEGATIVE")
	private double negative;

	@Column(name="NEUTRAL")
	private double neutral;

	@Column(name="POSITIVE")
	private double positive;

	@Column(name="SENTIMENT_SCORE")
	private double sentimentScore;

	@Column(name="SENTIMENT_TYPE")
	private String sentimentType;

	@Column(name="VERY_NEGATIVE")
	private double veryNegative;

	@Column(name="VERY_POSITIVE")
	private double veryPositive;

	//bi-directional one-to-one association to ReviewDetail
	@OneToOne
	@JoinColumn(name="REVIEW_ID")
	private ReviewDetail reviewDetail;

	public SentimentAnalysi() {
	}

	public int getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public double getNegative() {
		return this.negative;
	}

	public void setNegative(double negative) {
		this.negative = negative;
	}

	public double getNeutral() {
		return this.neutral;
	}

	public void setNeutral(double neutral) {
		this.neutral = neutral;
	}

	public double getPositive() {
		return this.positive;
	}

	public void setPositive(double positive) {
		this.positive = positive;
	}

	public double getSentimentScore() {
		return this.sentimentScore;
	}

	public void setSentimentScore(double sentimentScore) {
		this.sentimentScore = sentimentScore;
	}

	public String getSentimentType() {
		return this.sentimentType;
	}

	public void setSentimentType(String sentimentType) {
		this.sentimentType = sentimentType;
	}

	public double getVeryNegative() {
		return this.veryNegative;
	}

	public void setVeryNegative(double veryNegative) {
		this.veryNegative = veryNegative;
	}

	public double getVeryPositive() {
		return this.veryPositive;
	}

	public void setVeryPositive(double veryPositive) {
		this.veryPositive = veryPositive;
	}

	public ReviewDetail getReviewDetail() {
		return this.reviewDetail;
	}

	public void setReviewDetail(ReviewDetail reviewDetail) {
		this.reviewDetail = reviewDetail;
	}

}