package com.cdac.Services;



import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cdac.Entity.LaUserAction;
//import com.cdac.Dao.LaUserActionDao;


 



public interface LaUserActionService{
 
    
   
    public List<LaUserAction> listAll();
     
    public void save(LaUserAction la);
     
    public LaUserAction get(Integer id);
    //public LaUserAction getByUserIdAndSession(String id, String s);
    public void delete(Integer id);
    
    public List<LaUserAction> findByUserIdAndSessionId(String userid, String sessionId);
    
    public List<LaUserAction> findByUserIdandDaterange(String userid, Timestamp fromdate, Timestamp todate);
	
	public int getMaxsno(String userid,String sessionid);
	
	public int updateUserLogoutTime(Timestamp Logout, String userid, int sno, String sessionid);
}