package com.cdac.meghst.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.cdac.meghst.dtos.ContentDetailDTO;
import com.cdac.meghst.models.ContentDetail;

@Repository
public interface ContentDetailRepo extends JpaRepository<ContentDetail, Integer> {

	public final static String checkDirChildIdStatus = "SELECT CONTENT_ID as contentId FROM content_details WHERE DIR_CHILD_ID = ?1";

//	@Query(value = "SELECT count(DIR_CHILD_ID) from dir_details where DIR_CHILD_ID LIKE ?1%",nativeQuery = true)

	@Query(value = "SELECT CONTENT_NAME as name, CONTENT_TYPE as type, CONTENT_DURATION as duration,CONTENT_ID as content,PREVIEW_URL as url from content_details where DIR_CHILD_ID = ?1 && USER_ID = ?2", nativeQuery = true)
	List<ContentDetailInterface> getContentDetails(String cid, String userId);

	ContentDetail findByContentId(int contentId);

	@Query(value = checkDirChildIdStatus, nativeQuery = true)
	List<ContentIDInterface> findByDirChildId(String dirChildId);
}
