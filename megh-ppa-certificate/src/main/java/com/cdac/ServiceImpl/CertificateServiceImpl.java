package com.cdac.ServiceImpl;



import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cdac.Services.CertificateService;

import com.cdac.Entity.Certificate;

import com.cdac.Repository.CertificateRepository;



 
@Service
@Transactional
public class CertificateServiceImpl implements CertificateService {
 
    
    @Autowired
    private CertificateRepository repo;

    public Certificate save(Certificate la) {
    	return repo.save(la);    	
    }

	@Override
	public Certificate getByCertificateId(int certificate_id) {
		// TODO Auto-generated method stub
		return repo.findByCertificateId(certificate_id);
	}
	/*
	@Override
	public Certificate getByUserIdAndTemplateId(String user_id,int t) {
		return repo.findByUserIdAndTemplate_TemplateId(user_id,t);	
	}*/
 
	@Override
	public Certificate getByUserIdAndCourseId(String user_id, int course_id) {
		return repo.findByUserIdCourseId(user_id, course_id);	
	}
    /*
	 * public List<Assignment> listAll() { return repo.findAll(); }
	 */
     
         
	/*
	 * @Override public Assignment getByAssignmentId(int assign_id) {
	 * 
	 * return repo.findByAssignmentId(assign_id); } public void delete(Integer id) {
	 * repo.deleteById(id); }
	 */
	
}