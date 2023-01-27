package com.cdac.ServiceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cdac.Services.LaUserActionService;
import com.cdac.Entity.LaContentVisit;
import com.cdac.Entity.LaUserAction;
//import com.cdac.Dao.LaUserActionDao;
import com.cdac.Repository.LaUserActionRepository;

@Service
@Transactional
public class LaUserActionServiceImpl implements LaUserActionService {

	@Autowired
	private LaUserActionRepository repo;

	public List<LaUserAction> listAll() {
		return repo.findAll();
	}

	public void save(LaUserAction la) {
		repo.save(la);
	}

	public LaUserAction get(Integer id) {
		return repo.findById(id).get();

	}

	// public LaUserAction getByUserIdAndSession(String id, String s) {
	// return repo.findByUserIdAndSessionId(id,s);
	//
	// }
	public void delete(Integer id) {
		repo.deleteById(id);
	}

	@Override
	public List<LaUserAction> findByUserIdAndSessionId(String userid, String sessionId) {
		// TODO Auto-generated method stub
		return repo.findByUserIdAndSessionId(userid, sessionId);
	}

	@Override
	public int getMaxsno(String userid, String sessionid) {
		// TODO Auto-generated method stub
		return repo.getMaxsno(userid, sessionid);
	}

	@Override
	public int updateUserLogoutTime(Timestamp Logout, String userid, int sno, String sessionid) {
		// TODO Auto-generated method stub
		// System.out.println("-----------"+Logout);
		return repo.updateUserLogoutTime(Logout, userid, sno, sessionid);
	}

	@Override
	public List<LaUserAction> findByUserIdandDaterange(String userid, Timestamp fromdate, Timestamp todate) {
		// TODO Auto-generated method stub
		System.out.println("-----------" + fromdate + "---------" + todate);
		return repo.findByUserIdAndDaterange(userid, fromdate, todate);
	}
}