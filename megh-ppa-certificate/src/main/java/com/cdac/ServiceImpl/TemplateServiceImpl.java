package com.cdac.ServiceImpl;



import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cdac.Services.TemplateService;

import com.cdac.Entity.Template;

import com.cdac.Repository.TemplateRepository;



 
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {
 
    
    @Autowired
    private TemplateRepository repo;

     
	/*
	 * public List<Assignment> listAll() { return repo.findAll(); }
	 */
    @Override 
    public Template save(Template la) 
    { 
    	return repo.save(la);
    	}
	 
    @Override
	public Template getByTemplateId(int template_id) {
		
		return repo.findByTemplateId(template_id);
	}
    @Override
	 public void delete(Integer id) 
	 { 
		 repo.deleteById(id); 
	return;	 
	 }
	
	
}