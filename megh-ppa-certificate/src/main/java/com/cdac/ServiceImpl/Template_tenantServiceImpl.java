package com.cdac.ServiceImpl;



import java.util.List;
 
import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cdac.Services.Template_tenantService;


import com.cdac.Entity.Template_tenant;
import com.cdac.Repository.Template_tenantRepository;


 
@Service
@Transactional
public class Template_tenantServiceImpl implements Template_tenantService {
 
    
    @Autowired
    private Template_tenantRepository repo;
     
	/*
	 * public List<Assignment_tenant> listAll() { return repo.findAll(); }
	 
	 * @Override public List<Assignment_tenant>
	 * getByCreatedByAndCourseIdAndTenantId(String created_by, int course_id, int
	 * tenant_id) { // TODO Auto-generated method stub return
	 * repo.findByAssignment_CreatedByAndCourseIdAndTenantId(created_by,course_id,
	 * tenant_id); }
	 * 
	 
	 */
    
    @Override 
    public Template_tenant getByCourseIdAndTenantId(int course_id, int tenant_id)
    { // TODO Auto-generated method stub return
    		return  repo.findByCourseIdAndTenantId(course_id,tenant_id);
    		  
    }
    		 
    @Override
	  public void save(Template_tenant la)
    { repo.save(la);
    }
	   
    @Override
   	public Template_tenant getByTemplateId(int template_id)
 {
		// TODO Auto-generated method stub
		return repo.findByTemplate_TemplateId(template_id);
	}
	
}