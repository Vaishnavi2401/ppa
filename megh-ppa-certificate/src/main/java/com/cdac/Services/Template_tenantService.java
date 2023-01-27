package com.cdac.Services;



import java.util.List;

import javax.validation.constraints.Pattern;

import com.cdac.Entity.Template_tenant;
//import com.cdac.Dao.LaUserActionDao;



 

public interface Template_tenantService{
  
    
	/*
	 * public List<Template_tenant> listAll();
	
	
	 * public List<Template_tenant> getByCourseIdAndTenantId(int course_id, int
	 * tenant_id);
	 * public void delete(Integer id);
	 */
   	public Template_tenant getByTemplateId(int template_id);
    public void save(Template_tenant la);
    public Template_tenant getByCourseIdAndTenantId(int course_id, int tenant_id);
    		 

}