package com.cdac.Services;





import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import com.cdac.Entity.LaContentVisit;
import com.cdac.Utils.LaContentAccess;
import com.cdac.Utils.LaContentTimespent;
//import com.cdac.Dao.LaUserActionDao;



 

public interface LaContentVisitService{
  
    
     
    public List<LaContentVisit> listAll();
     
    public void save(LaContentVisit la);
     
    public LaContentVisit get(Integer id);
    public LaContentVisit getByUserIdAndSession(String id, String s);
    public void delete(Integer id);

	public List<LaContentVisit> getByUserIdAndCourseId(String userid,String courseid);
	
	public List<LaContentTimespent> getByUserIdAndCourseIdAndDaterangetimediff(String userid,String courseid, Timestamp fromdate, Timestamp todate);
	
	public List<LaContentVisit> getByUserIdAndCourseIdAndDaterange(String userid,String courseid, Timestamp fromdate, Timestamp todate);
	
	public int getMaxsno(String userid,String sessionid);
	
	public int updateContentVisitTime(Timestamp outTime, String userid, int sno, String sessionid);
}