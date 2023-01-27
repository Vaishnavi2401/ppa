package com.cdac.meghst.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.meghst.models.DirDetail;

@Repository
public interface DirDetailRepo extends JpaRepository<DirDetail, String> {

	//@Query("SELECT count(dirChildId) from DirDetail where dirChildId LIKE ?1%")
	//public int getChildCount(char childChar);
	
	@Query(value = "SELECT IFNULL(MAX(0 +SUBSTRING(`DIR_CHILD_ID`,2)),0) FROM dir_details where DIR_CHILD_ID LIKE ?1%", nativeQuery = true)
	public int getChildCount(char childChar);

	@Query("SELECT dirParentId from DirDetail where dirChildId = ?1")
	public String getParentId(String childId);

	Optional<DirDetail> findByDirChildIdAndLastModifiedBy(String parentId, String userId);

	List<DirDetail> findByDirParentIdAndLastModifiedBy(String parentId,String userId);
	
	List<DirDetail> findByDirChildId(String childId);

	List<DirDetail> findByLastModifiedBy(String userId);

	@Query("SELECT jsonData from DirDetail where dirChildId = ?1")
	public String getJsonData(String data);

	Optional<DirDetail> findByDirParentIdAndDirChildId(String parentId, String ChildId);
	
	@Query("SELECT jsonData from DirDetail where lastModifiedBy = ?1 and dirParentId = dirChildId")
	public List<?> getJsonDetails(String userId);
	
	@Modifying
	@Transactional
	@Query("UPDATE DirDetail SET jsonData = ?1 where dirParentId = ?2 and dirChildId = ?3")
	public void updateJsonDetails(String jsonData,String pid,String cid);	
	
	
	@Modifying
	@Transactional
	@Query("DELETE from DirDetail where dirParentId = ?1")
	public void deleteParentDirectory(String pid);
	
	@Modifying
	@Transactional
	@Query("DELETE from DirDetail where dirChildId = ?1")
	public void deleteChildDirectory(String cid);
	
}
