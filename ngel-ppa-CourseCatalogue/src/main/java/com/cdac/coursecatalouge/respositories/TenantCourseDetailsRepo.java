package com.cdac.coursecatalouge.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.coursecatalouge.models.TenantCourseDetail;
import com.cdac.coursecatalouge.models.TenantCourseDetailPK;
import com.cdac.coursecatalouge.models.UserTenantCourseMap;

@Repository
public interface TenantCourseDetailsRepo extends JpaRepository<TenantCourseDetail, TenantCourseDetailPK> {

	public final static String getCoursesById = "select * from `tenant_course_details` where course_id  = ?1";

	public final static String deleteCoursesById = "delete * from `tenant_course_details` where course_id  = ?1";
	

	@Query(value = getCoursesById, nativeQuery = true)
	List<TenantCourseDetail> findCoursesBycourseId(int courseId);

	@Query(value = deleteCoursesById, nativeQuery = true)
	List<TenantCourseDetail> deleteCourseByCourseId(int courseId);
	
	@Query(value = "select tcd.role_id from user_tenant_course_map tcd where tcd.course_id=:courseId and tcd.user_id=:userId", nativeQuery = true)
	int findbycourseIdandUserId(int courseId, String userId);


//	TenantCourseDetail findBy

}
