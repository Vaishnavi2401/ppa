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

import com.cdac.dao.DesignationDAO;
import com.cdac.model.DesignationMaster;

@CrossOrigin("*")
@RestController
@RequestMapping("/um_api")
public class DesignationMasterController {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";
	public static final String NOTFOUND = "Notfound";
	
	@Autowired
	private DesignationDAO designationDAO;
	
	@PostMapping("/desig")
	public String save(@RequestBody DesignationMaster desigMaster) {
		try {
			designationDAO.save(desigMaster);
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
			
	}
	

	@GetMapping("/desig")
	public List<DesignationMaster> get(){
		return designationDAO.findAll();
	}
	
	@GetMapping("/desig/{id}")
	public DesignationMaster get(@PathVariable int id) {
		Optional<DesignationMaster> desigObj = designationDAO.findById(id);
		if(desigObj.isPresent()) {
			return desigObj.get();
		}else {
			throw new RuntimeException("Designation is not found for the Id:" + id);
		}
	}


	@PutMapping("/desig")
	public String update(@RequestBody DesignationMaster desigObj) {
		try {
			designationDAO.save(desigObj);
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
		
	}
	
	@DeleteMapping("/desig/{id}")
	public String delete(@PathVariable int id) {
		Optional<DesignationMaster> desig = designationDAO.findById(id);
		if(desig.isPresent()) {
			designationDAO.delete(desig.get());
			return SUCCESS;
		}else {
			return FAILED;
		}
	}
}
