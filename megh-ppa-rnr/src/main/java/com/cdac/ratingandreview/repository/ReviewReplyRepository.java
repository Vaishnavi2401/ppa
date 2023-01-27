package com.cdac.ratingandreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.ratingandreview.model.ReplyReview;
import com.cdac.ratingandreview.model.ReviewDetail;

@Repository
public interface ReviewReplyRepository extends JpaRepository<ReplyReview, Integer>{

	ReplyReview findByReplyId(int replyId);
	List<ReplyReview> findByReviewDetail(ReviewDetail reviewDetail);
	
}
