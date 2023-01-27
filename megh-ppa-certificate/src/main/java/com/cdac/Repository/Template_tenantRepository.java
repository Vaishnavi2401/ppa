package com.cdac.Repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cdac.Entity.Template_tenant;
 
public interface Template_tenantRepository extends JpaRepository<Template_tenant, Integer> {
	 
	/*
	 * public List<Assignment_tenant>
	 * findByAssignment_CreatedByAndCourseIdAndTenantId(String created_by, int
	 * course_id, int tenant_id);
	
	 * public Assignment_tenant findByAssignment_AssignmentId(int assign_id); public
	 * void deleteByAssignment_AssignmentId(int id);
	 */
	public Template_tenant findByTemplate_TemplateId(int template_id);
    public Template_tenant findByCourseIdAndTenantId(int course_id, int tenant_id);
	  
}
