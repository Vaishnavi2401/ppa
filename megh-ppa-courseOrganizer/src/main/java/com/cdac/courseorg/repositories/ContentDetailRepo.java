package com.cdac.courseorg.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.courseorg.models.ContentDetail;


public interface ContentDetailRepo extends JpaRepository<ContentDetail, Integer> {

	List<ContentDetail> findByCourseId(String courseId);

	List<ContentDetail> findBypContentId(int p_content_id);


}
