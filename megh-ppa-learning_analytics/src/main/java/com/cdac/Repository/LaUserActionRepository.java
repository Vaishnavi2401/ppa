package com.cdac.Repository;
 
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdac.Entity.LaUserAction;
 
public interface LaUserActionRepository extends JpaRepository<LaUserAction, Integer> {
	//public LaUserAction	findByUserIdAndSessionId(String id,String s); 
	
	public List<LaUserAction> findByUserIdAndSessionId(String userid, String sessionId); 
	
	
	@Query(value = "select la.sno,la.session_id, la.site_id,la.user_id,la.visit_last_action_time,la.Logout,la.ip_address,la.action,la.config_os,la.config_resolution,la.config_browser from la_user_action la where la.user_id=:userId and la.visit_last_action_time between :fromdate and :todate", nativeQuery = true)
	public List<LaUserAction> findByUserIdAndDaterange(@Param("userId") String userId,@Param("fromdate") Timestamp fromdate,@Param("todate") Timestamp todate);
	
	@Modifying
    @Query(value = "update la_user_action lc set lc.Logout=:Logout, action='Logout' where lc.user_id=:userId and lc.sno=:sno and lc.session_id=:sessionId", nativeQuery = true)
	public int updateUserLogoutTime(@Param("Logout") Timestamp  Logout, @Param("userId") String userId,  @Param("sno") int sno, @Param("sessionId") String sessionId);
	
	@Query(value = "SELECT max(sno) FROM la_user_action lc where lc.user_id=:userId and lc.session_id=:sessionId",nativeQuery = true)
	int getMaxsno(@Param("userId") String userId,  @Param("sessionId") String sessionId);
}
