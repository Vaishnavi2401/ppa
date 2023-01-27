package com.cdac.Repository;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cdac.Entity.Template;
 
public interface TemplateRepository extends JpaRepository<Template, Integer> {

	public Template findByTemplateId(int template_id);
	/*
	 * public Assignment findByUserIdAndSessionId(String id,String s);
	 * 
	 * public List<Assignment> findByUserIdAndCourseId(String userid, String
	 * courseid);
	 */
	
}
