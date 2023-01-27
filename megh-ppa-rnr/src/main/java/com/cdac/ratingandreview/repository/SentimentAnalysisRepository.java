package com.cdac.ratingandreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cdac.ratingandreview.model.ReviewDetail;
import com.cdac.ratingandreview.model.ReviewMaster;
import com.cdac.ratingandreview.model.SentimentAnalysi;

@Repository
public interface SentimentAnalysisRepository extends JpaRepository<SentimentAnalysi, Integer>{

	 SentimentAnalysi findByReviewId(int reviewId);
}
