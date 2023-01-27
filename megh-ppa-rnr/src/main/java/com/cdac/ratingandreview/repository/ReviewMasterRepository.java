package com.cdac.ratingandreview.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.ratingandreview.model.ReviewDetail;
import com.cdac.ratingandreview.model.ReviewMaster;

@Repository
public interface ReviewMasterRepository extends JpaRepository<ReviewMaster, Integer>{
	//List<ReviewMaster> findByIdProgramIdAndIdSessionDate(int programId,Date sessionDate);
	  List<ReviewMaster> findByIdLearnerIdAndIdRviewItemId(String learnerId, String rviewItemId);
	  List<ReviewMaster> findByIdRviewItemId(String rviewItemId);
	  ReviewMaster findByReviewDetail(ReviewDetail reviewDetail);
}
