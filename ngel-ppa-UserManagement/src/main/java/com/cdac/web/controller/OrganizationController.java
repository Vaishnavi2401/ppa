package com.cdac.web.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dao.OrganizationDAO;
import com.cdac.dto.OrgMasterPojo;
import com.cdac.model.OrgMaster;

@RestController
@RequestMapping("/um_api")
public class OrganizationController {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";
	
	
	@Autowired
	private OrganizationDAO orgDAO;
	
	
	@PostMapping("/org")
	public String save(@RequestBody OrgMasterPojo orgMasterpojo) {
		try {
			OrgMaster orgMaster =new OrgMaster();
			orgMaster.setOrgName(orgMasterpojo.getOrgName()); 
			orgMaster.setOrgAddress(orgMasterpojo.getOrgAddress());
			orgMaster.setOrgCode(orgMasterpojo.getOrgCode());
			
			OrgMaster orgMaster1=	orgDAO.save(orgMaster);
			
			//Adding institute details into Assessment ApplicationDetail Database
			
			//quizClient.addApplicationDetails(orgMaster1.getOrgId(), orgMasterpojo.getOrgName());
			
			
			
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
		
	}
	

	@GetMapping("/org")
	public List<OrgMaster> get(){
		
		
		return orgDAO.findAll();
	}
	
	@GetMapping("/org/{id}")
	public OrgMaster get(@PathVariable int id) {
		Optional<OrgMaster> orgObj = orgDAO.findById(id);
		if(orgObj.isPresent()) {
			return orgObj.get();
		}else {
			throw new RuntimeException("org is not found for the Id:" + id);
		}
	}


	@PutMapping("/org")
	public String update(@RequestBody  OrgMasterPojo orgMasterpojo) {
		
		Optional<OrgMaster> orgObj = orgDAO.findById(orgMasterpojo.getOrgId());
		if(orgObj.isPresent()) {
			OrgMaster orgMaster =orgObj.get();
			orgMaster.setOrgName(orgMasterpojo.getOrgName()); 
			orgMaster.setOrgAddress(orgMasterpojo.getOrgAddress());
			orgMaster.setOrgCode(orgMasterpojo.getOrgCode());
			 orgDAO.save(orgMaster);
			 return SUCCESS;
		}
		else {
			return FAILED;	
		}
		
		 	
	}
	
	
	
	@DeleteMapping("/org/{id}")
	public String delete(@PathVariable int id) {
		Optional<OrgMaster> org = orgDAO.findById(id);
		if(org.isPresent()) {
			orgDAO.delete(org.get());
			return SUCCESS;
		}else {
			//throw new RuntimeException("org is not found for the id "+id);
			return FAILED;
		}
	}
}
