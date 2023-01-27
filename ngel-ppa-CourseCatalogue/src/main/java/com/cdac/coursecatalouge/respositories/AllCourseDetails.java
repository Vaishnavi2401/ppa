package com.cdac.coursecatalouge.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.coursecatalouge.models.TenantCourseDetail;
import com.cdac.coursecatalouge.models.TenantCourseDetailPK;

@Repository
public interface AllCourseDetails
		extends JpaRepository<TenantCourseDetail, TenantCourseDetailPK>, JpaSpecificationExecutor<TenantCourseDetail> {

	public final static String courses = "select t.tenant_id as tenantId,t.course_id as courseId,t.course_name as courseName,t.course_description as courseDescription,t.enroll_edate as courseEDate, t.enroll_sdate as courseSDate ,t.publish_date as publishDate,t.course_closing_date as courseClosingDate, DATEDIFF(t.course_closing_date, t.commencement_date) as duration, t.image_url as imageUrl,t.category_id as categoryId,t.category as category,t.status as status, t.Course_Type as courseType,t.Course_Fee as courseFee from tenant_course_details t where t.course_id!=100";

	@Query(value = courses, nativeQuery = true)
	List<TenantCourse> findCourses();

	public final static String coursesByCourseId = "select t.tenant_id as tenantId,t.course_id as courseId,t.course_name as courseName,t.course_description as courseDescription, t.publish_date as publishDate,t.course_closing_date as courseClosingDate,DATEDIFF(t.course_closing_date, t.commencement_date) as duration,t.image_url as imageUrl,t.category_id as categoryId,t.category as category,t.Course_Type as courseType,t.Course_Fee as courseFee from tenant_course_details t where  t.course_id!=100 and t.course_id IN :courseIds and t.tenant_id=:tenantId";

	//public final static String courseByCourseId = "select t.tenant_id as tenantId,t.course_id as courseId,t.course_name as courseName,t.course_description as courseDescription, t.publish_date as publishDate,t.course_closing_date as courseClosingDate,DATEDIFF(t.course_closing_date, t.commencement_date) as duration,t.image_url as imageUrl,t.category_id as categoryId,t.category as category,t.Course_Type as courseType,t.Course_Fee as courseFee from tenant_course_details t where  t.course_id!=100 and t.course_id =:courseId and t.tenant_id=:tenantId";
	public final static String courseByCourseId = "select t.tenant_id as tenantId,t.course_id as courseId,t.course_name as courseName,t.course_description as courseDescription, t.publish_date as publishDate,t.commencement_date as courseSDate, t.course_closing_date as courseClosingDate,DATEDIFF(t.course_closing_date, t.commencement_date) as duration,t.image_url as imageUrl,t.category_id as categoryId,t.category as category, t.Course_Type as courseType,t.Course_Fee as courseFee from tenant_course_details t where  t.course_id!=100 and t.course_id =:courseId and t.tenant_id=:tenantId";
	@Query(value = coursesByCourseId, nativeQuery = true)
	List<TenantCourse> findCoursesByCourseId(@Param("courseIds") List<Integer> courseIds,
			@Param("tenantId") int tenantId);
	
	@Query(value = courseByCourseId, nativeQuery = true)
	List<TenantCourse> findCourseByCourseId(@Param("courseId") int courseId,@Param("tenantId") int tenantId);

	public final static String coursesByUser = "select t.tenant_id as tenantId,t.course_id as courseId,t.course_name as courseName,t.course_description as courseDescription,t.publish_date as publishDate,t.course_closing_date as courseClosingDate,DATEDIFF(t.course_closing_date, t.commencement_date) as duration,t.image_url as imageUrl,t.category_id as categoryId,t.category as category,t.Course_Type as courseType,t.Course_Fee as courseFee from tenant_course_details t,user_tenant_course_map  u where u.user_id=:userId and u.role_id=4 and t.course_id=u.course_id and t.course_id!=100 and t.tenant_id=u.tenant_id";

	@Query(value = coursesByUser, nativeQuery = true)
	List<TenantCourse> findCoursesByUserId(@Param("userId") String userId);

	public interface TenantCourse {
		Integer getTenantId();

		Integer getCourseId();

		String getCourseName();

		String getCourseDescription();

		String getPublishDate();

		String getCourseClosingDate();

		String getCourseEDate();

		String getCourseSDate();

		Integer getDuration();

		String getImageUrl();

		Integer getCategoryId();

		String getCategory();

		String getCourseType();

		Double getCourseFee();

		Integer getRoleId();

		String getUserId();

		String getStatus();
	}

}
