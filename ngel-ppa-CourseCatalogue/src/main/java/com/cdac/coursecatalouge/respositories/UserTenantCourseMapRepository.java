package com.cdac.coursecatalouge.respositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdac.coursecatalouge.models.UserTenantCourseMap;
import com.cdac.coursecatalouge.models.UserTenantCourseMapPK;

public interface UserTenantCourseMapRepository extends JpaRepository<UserTenantCourseMap, UserTenantCourseMapPK>,
		JpaSpecificationExecutor<UserTenantCourseMap> {

	// for faculty count
	public final static String facultyCount = "SELECT DISTINCT(user_id) FROM `user_tenant_course_map` where role_id=?1";

	public final static String getCoursesEnrolledByUser = "select * from `user_tenant_course_map` where user_id  =:userId and role_id=:roleId";
	
	public final static String getCoursesDetailsEnrolledByUser = "select * from `user_tenant_course_map` where user_id  =:userId and course_id=:courseId";

	public final static String findRoleIdForUserOrInstructorCourseStatusCheck = "select role_id from user_tenant_course_map where user_id= :userId AND course_id= :courseId";
	
	public final static String updateCourseEnrollmentStatus="UPDATE user_tenant_course_map SET role_id =:roleId, enroll_approv_rejec_date=:currenttime WHERE user_id =:userId AND course_id =:courseId AND role_id =4";

	
	
	@Query(value = facultyCount, nativeQuery = true)
	List<String> getDistinctIdUserIdByIdRoleId(int roleId);

	// for user count - no distinct so more user count

	List<UserTenantCourseMap> findByIdRoleId(int roleId);

	@Query(value = getCoursesEnrolledByUser, nativeQuery = true)
	List<UserTenantCourseMap> findCoursesByuserIdAndRoleId(String userId, int roleId);

	@Query(value = getCoursesDetailsEnrolledByUser, nativeQuery = true)
	List<UserTenantCourseMap> findCoursesByuserIdAndCourseId(String userId, int courseId);
	
	public final static String authorByCourseId = "select utcm.user_id as author from user_tenant_course_map utcm where utcm.course_id=:courseId and utcm.tenant_id=:tenantId and utcm.role_id=2 and utcm.user_id!='ADMINISTRATOR'";

	@Query(value = authorByCourseId, nativeQuery = true)
	List<String> findAuthorsByCourseId(@Param("courseId") Integer courseId, @Param("tenantId") Integer tenantId);

	public final static String usersCount = "select count(utcm.user_id) as count from user_tenant_course_map utcm where utcm.course_id=:courseId and utcm.tenant_id=:tenantId and utcm.role_id=1 and utcm.user_id!='ADMINISTRATOR'";

	@Query(value = usersCount, nativeQuery = true)
	Integer findUserCount(@Param("courseId") Integer courseId, @Param("tenantId") Integer tenantId);

	Optional<UserTenantCourseMap> findByIdUserIdAndIdCourseIdAndIdRoleId(String userId, int courseId, int roleId);

	@Query(value = findRoleIdForUserOrInstructorCourseStatusCheck, nativeQuery = true)
	Integer findByIdUserIdAndCourseIdUserOrInstructorCourseStatus(@Param("userId") String userId,
			@Param("courseId") int courseId);
	
	List<UserTenantCourseMap> findByIdRoleIdAndIdCourseId(int roleId, int courseId);

	@Modifying
	@Query(value = updateCourseEnrollmentStatus, nativeQuery = true)
	void updateUserCourseEnrollStatus(@Param("roleId") int roleId, @Param("userId") String userId,@Param("courseId") int courseId, @Param("currenttime") Timestamp currenttime);
	
	
}
