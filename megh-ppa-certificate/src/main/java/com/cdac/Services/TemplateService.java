package com.cdac.Services;



import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Pattern;


import com.cdac.Entity.Template;



 

public interface TemplateService{
  
    
     
    //public List<Template> listAll();
     
    public Template save(Template la);
     
	
    public void delete(Integer id);

	public Template getByTemplateId(int template_id);

	}