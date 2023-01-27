package com.cdac.courseorg.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.courseorg.models.DirDetail;

public interface DirDetailsRepo extends JpaRepository<DirDetail, String>{

	//@Query(value = "SELECT count(DIR_CHILD_ID) from dir_details where DIR_CHILD_ID LIKE ?1%",nativeQuery = true)
	//public int getChildCount(char childChar);
	
	@Query(value = "SELECT IFNULL(MAX(0 +SUBSTRING(`DIR_CHILD_ID`,2)),0) FROM dir_details where DIR_CHILD_ID LIKE ?1%",nativeQuery = true)
	public int getChildCount(char childChar);
	
	@Query(value = "SELECT DIR_PARENT_ID from dir_details where DIR_CHILD_ID = ?1",nativeQuery = true)
	public String getParentId(String childId);
	
	List<DirDetail> findByLastModifiedBy(String userId);
	
	List<DirDetail> findByDirParentIdAndLastModifiedBy(String parentId,String userId);
	
	List<DirDetail> findByDirChildId(String childId);
	
	Optional<DirDetail> findByDirChildIdAndLastModifiedBy(String parentId, String userId);
	
	@Modifying
	@Transactional
	@Query("DELETE from DirDetail where dirParentId = ?1")
	public void deleteParentDirectory(String pid);
	
	@Modifying
	@Transactional
	@Query("DELETE from DirDetail where dirChildId = ?1")
	public void deleteChildDirectory(String cid);
}
