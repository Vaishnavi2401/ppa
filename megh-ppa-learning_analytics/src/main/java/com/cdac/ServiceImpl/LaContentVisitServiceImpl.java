package com.cdac.ServiceImpl;




import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cdac.Services.LaContentVisitService;
import com.cdac.Utils.LaContentAccess;
import com.cdac.Utils.LaContentTimespent;
import com.cdac.Entity.LaContentVisit;
//import com.cdac.Dao.LaUserActionDao;
import com.cdac.Repository.LaContentVisitRepository;


 
@Service
@Transactional
public class LaContentVisitServiceImpl implements LaContentVisitService {
 
    
    @Autowired
    private LaContentVisitRepository repo;
     
    public List<LaContentVisit> listAll() {
        return repo.findAll();
    }
     
    public void save(LaContentVisit la) {
    	repo.save(la);    	
    }
     
    public LaContentVisit get(Integer id) {
        return repo.findById(id).get();
        
    }
    public LaContentVisit getByUserIdAndSession(String id, String s) {
        return repo.findByUserIdAndSessionId(id,s);
        
    }
    public void delete(Integer id) {
        repo.deleteById(id);
    }

	@Override
	public List<LaContentVisit> getByUserIdAndCourseId(String userid, String courseid) {
		// TODO Auto-generated method stub
		return repo.findByUserIdAndCourseId(userid,courseid);
	}

	@Override
	public int getMaxsno(String userid, String sessionid) {
		// TODO Auto-generated method stub
		return repo.getMaxsno(userid,sessionid);
	}

	@Override
	public int updateContentVisitTime(Timestamp outTime, String userid, int sno, String sessionid) {
		// TODO Auto-generated method stub
		return repo.updateContentAccessTime(outTime, userid, sno, sessionid);
	}

	@Override
	public List<LaContentTimespent> getByUserIdAndCourseIdAndDaterangetimediff(String userid, String courseid, Timestamp fromdate,Timestamp todate) {
		// TODO Auto-generated method stub
		//System.out.println("------------"+userid+"----"+courseid+"---"+fromdate+"--"+todate);
		List<Object[]> timespentlist=repo.findByUserIdAndCourseIdandDaterangetimediff(userid,courseid,fromdate,todate);
		List<LaContentTimespent> laconttimespentlist=new ArrayList<LaContentTimespent>();
		
		
		for (Object[] lacontimespent : timespentlist) {
            LaContentTimespent lcs=new LaContentTimespent();
            
            lcs.setResTitle((String)lacontimespent[0]);
            lcs.setSpentTime(lacontimespent[1].toString());
            laconttimespentlist.add(lcs);
		}

		
		
		return laconttimespentlist;
	}
	
	@Override
	public List<LaContentVisit> getByUserIdAndCourseIdAndDaterange(String userid, String courseid, Timestamp fromdate,Timestamp todate) {
		// TODO Auto-generated method stub
		//System.out.println("------------"+userid+"----"+courseid+"---"+fromdate+"--"+todate);
		return repo.findByUserIdAndCourseIdandDaterange(userid,courseid,fromdate,todate);
	}
}