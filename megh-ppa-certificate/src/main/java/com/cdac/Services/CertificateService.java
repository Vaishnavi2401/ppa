package com.cdac.Services;



import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Pattern;

import com.cdac.Entity.Certificate;




 

public interface CertificateService{
  
    
     
   // public List<Certificate> listAll();
     
    public Certificate save(Certificate la);
     
	
    //public void delete(Integer id);

	public Certificate getByCertificateId(int certificate_id);
	//public Certificate getByUserIdAndTemplateId(String user_id,int t);
	public Certificate getByUserIdAndCourseId(String user_id, int course_id);

	}