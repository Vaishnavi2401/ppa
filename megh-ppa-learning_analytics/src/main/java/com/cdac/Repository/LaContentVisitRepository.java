package com.cdac.Repository;
 

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdac.Entity.LaContentVisit;
 
public interface LaContentVisitRepository extends JpaRepository<LaContentVisit, Integer> {
	public LaContentVisit	findByUserIdAndSessionId(String id,String s);

	public List<LaContentVisit> findByUserIdAndCourseId(String userid, String courseid); 
	
	
	
	@Query(value="select lc.res_title AS resTitle, SEC_TO_TIME(SUM(TIME_TO_SEC(timediff(lc.out_time, lc.in_time)))) AS spentTime FROM la_content_visits lc where lc.user_id=:userId and lc.course_id=:courseId and lc.in_time>=:fromdate and lc.out_time<=:todate group by lc.res_id",  nativeQuery = true)
	public List<Object[]> findByUserIdAndCourseIdandDaterangetimediff(@Param("userId") String userId, @Param("courseId") String courseId, @Param("fromdate") Timestamp fromdate,@Param("todate") Timestamp todate);
			
	@Query(value = "select lc.sno, lc.res_id,lc.res_title,lc.session_id,lc.filetype, lc.user_id,lc.course_id,lc.res_title,lc.in_time,lc.out_time from la_content_visits lc where lc.user_id=:userId and lc.course_id=:courseId and lc.in_time>=:fromdate and lc.out_time<=:todate", nativeQuery = true)
	public List<LaContentVisit> findByUserIdAndCourseIdandDaterange(@Param("userId") String userId, @Param("courseId") String courseId, @Param("fromdate") Timestamp fromdate,@Param("todate") Timestamp todate);
	
	@Modifying
    @Query(value = "update la_content_visits lc set out_time=:outTime where lc.user_id=:userId and lc.SNO=:sno and lc.session_id=:sessionId", nativeQuery = true)
	public int updateContentAccessTime(@Param("outTime") Timestamp  outTime, @Param("userId") String userId,  @Param("sno") int sno, @Param("sessionId") String sessionId);
	
	@Query(value = "SELECT max(SNO) FROM la_content_visits lc where lc.user_id=:userId and lc.session_id=:sessionId",nativeQuery = true)
	int getMaxsno(@Param("userId") String userId,  @Param("sessionId") String sessionId);
	
}
