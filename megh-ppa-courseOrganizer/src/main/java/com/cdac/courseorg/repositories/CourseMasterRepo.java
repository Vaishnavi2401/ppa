package com.cdac.courseorg.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.cdac.courseorg.models.CourseMaster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CourseMasterRepo extends JpaRepository<CourseMaster, Integer> {

	public final static String getAllCourses = "SELECT master.course_id as courseId,category_name as categoryName, course_name as courseName, duration as duration, course_access_type as course_access_type,  general_details as generalDetails, prerequisite as prerequisite,  course_objective as objective, inst_profile as inst_profile, course_type as courseType, course_fee as courseFee, fee_discount as fee_discount, publish_date as publishDate, enroll_sdate as enrollStartDate, enroll_edate as enrollEndDate, commencement_date as commenceDate, course_closing_date as closingDate, video as video, banner as banner, image_url as imageUrl from course_category category, course_master master, course_schedule schedule where category.category_id = master.category_id AND master.course_id = schedule.course_id";
	public final static String getPublishCourses = "SELECT master.course_id as courseId,category_name as categoryName, course_name as courseName, duration as duration,course_access_type as course_access_type, general_details as generalDetails, prerequisite as prerequisite, course_objective as objective, inst_profile as inst_profile, course_type as courseType, course_fee as courseFee, fee_discount as fee_discount, publish_date as publishDate, enroll_sdate as enrollStartDate, enroll_edate as enrollEndDate, commencement_date as commenceDate, course_closing_date as closingDate, video as video, banner as banner, image_url as imageUrl from course_category category, course_master master, course_schedule schedule where category.category_id = master.category_id AND master.course_id = schedule.course_id AND status = 'P'";
	public final static String getCourseById = "SELECT master.course_id as courseId,master.category_id as categoryId,category_name as categoryName, course_name as courseName, duration as duration,course_access_type as course_access_type, general_details as generalDetails, prerequisite as prerequisite, course_objective as objective, inst_profile as inst_profile, course_type as courseType, course_fee as courseFee, fee_discount as fee_discount, publish_date as publishDate, enroll_sdate as enrollStartDate, enroll_edate as enrollEndDate, commencement_date as commenceDate, course_closing_date as closingDate, video as video, banner as banner, image_url as imageUrl, course_structure_json as courseStructureJson, status as status from course_category category, course_master master, course_schedule schedule where category.category_id = master.category_id AND master.course_id = schedule.course_id AND master.course_id = ?1";
	public final static String getCourseByuserId = "SELECT master.course_id as courseId,master.category_id as categoryId,category_name as categoryName, course_name as courseName, duration as duration,course_access_type as course_access_type, general_details as generalDetails, prerequisite as prerequisite, course_objective as objective, inst_profile as inst_profile, course_type as courseType, course_fee as courseFee, fee_discount as fee_discount, publish_date as publishDate, enroll_sdate as enrollStartDate, enroll_edate as enrollEndDate, commencement_date as commenceDate, course_closing_date as closingDate, video as video, banner as banner, image_url as imageUrl, course_structure_json as courseStructureJson, status as status from course_category category, course_master master, course_schedule schedule where category.category_id = master.category_id AND master.course_id = schedule.course_id AND master.user_id = ?1";

	@Query(value = getAllCourses, nativeQuery = true)
	public List<UpdatedCourseMasterInterface> findAllCourses();

	@Query(value = getCourseById, nativeQuery = true)
	public UpdatedCourseMasterInterface findCourseById(int id);

	@Query(value = getCourseByuserId, nativeQuery = true)
	public List<UpdatedCourseMasterInterface> findCourseByuserId(String userId);

	@Query(value = "select course_structure_json from course_master where DIR_CHILD_ID = ?1", nativeQuery = true)
	public String getJsonData(String data);

	@Query(value = getPublishCourses, nativeQuery = true)
	public List<UpdatedCourseMasterInterface> findAllPublishCourses();

	@Query(value = "select course_id from course_master where  DIR_CHILD_ID = ?1", nativeQuery = true)
	public String getCourseId(String dirId);

//	@Query(value = "select user_id from course_master where course_id = ?1",nativeQuery = true)
//	public String getUserId(int courseId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE course_master set course_structure_json = ?1 where DIR_CHILD_ID = ?2", nativeQuery = true)
	public void updateJsonDetails(String jsonData, String pid);


}
