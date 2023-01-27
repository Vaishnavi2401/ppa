package com.cdac.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dao.QualificationDAO;
import com.cdac.model.QualificationMaster;

@CrossOrigin("*")
@RestController
@RequestMapping("/um_api")
public class QualificationMasterController {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";
	public static final String NOTFOUND = "Notfound";
	
	@Autowired
	private QualificationDAO qualificationDAO;
	
	@PostMapping("/qualf")
	public String save(@RequestBody QualificationMaster qualificationMaster) {
		try {
			qualificationDAO.save(qualificationMaster);
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
			
	}
	

	@GetMapping("/qualf")
	public List<QualificationMaster> get(){
		return qualificationDAO.findAll();
	}
	
	@GetMapping("/qualf/{id}")
	public QualificationMaster get(@PathVariable int id) {
		Optional<QualificationMaster> qualfObj = qualificationDAO.findById(id);
		if(qualfObj.isPresent()) {
			return qualfObj.get();
		}else {
			throw new RuntimeException("Qualification is not found for the Id:" + id);
		}
	}


	@PutMapping("/qualf")
	public String update(@RequestBody QualificationMaster qualfObj) {
		try {
			qualificationDAO.save(qualfObj);
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
		
	}
	
	@DeleteMapping("/qualf/{id}")
	public String delete(@PathVariable int id) {
		Optional<QualificationMaster> qualf = qualificationDAO.findById(id);
		if(qualf.isPresent()) {
			qualificationDAO.delete(qualf.get());
			return SUCCESS;
		}else {
			return FAILED;
		}
	}
}
