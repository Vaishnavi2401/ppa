package com.cdac.ratingandreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.cdac.ratingandreview.model.ReviewDetail;
import com.cdac.ratingandreview.model.ReviewMaster;

@Repository
public interface ReviewDetailRepository extends JpaRepository<ReviewDetail, Integer>{

	ReviewDetail findByReviewId(int reviewId);
	ReviewDetail findByReviewIdAndTenantId(int reviewId, int tenantId);
}
