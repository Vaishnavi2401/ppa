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

import com.cdac.dao.DistrictDAO;
import com.cdac.dao.StateDAO;
import com.cdac.exception.ResourceNotFoundException;
import com.cdac.model.DistrictMaster;
import com.cdac.model.StateMaster;
import com.cdac.dto.DistrictMasterDTO;
@CrossOrigin("*")
@RestController
@RequestMapping("/um_api")
public class DistrictController {
	
	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";

	@Autowired
	private DistrictDAO districtDAO;
	
	@Autowired
	private StateDAO stateDAO;
	
	
	@PostMapping("/district")
	public String save(@RequestBody DistrictMasterDTO districtMasterdto) {
		
	      int stateId=districtMasterdto.getStateId();
           DistrictMaster dm=new DistrictMaster();
		      dm.setDistrictId(districtMasterdto.getDistrictId());
		      dm.setDistrictName(districtMasterdto.getDistrictName());
		      return stateDAO.findById(stateId).map(stateMaster -> {
		    	  dm.setStateMaster(stateMaster);
			//stateMaster.setCountryMaster(countryMaster);
		    	  districtDAO.save(dm);
			return SUCCESS;
		}).orElseThrow(() -> new ResourceNotFoundException("state Id " + stateId + " not found"));
		
		
		}
	
	
	
	@GetMapping("/district/byStateId/{id}")
	public List<DistrictMaster> getdistrictsByStateId(@PathVariable int id) {
		
		StateMaster sm =new StateMaster();
		sm.setStateId(id);
		List<DistrictMaster> distObj = districtDAO.findBystateMaster(sm);
			if (!distObj.isEmpty()) {
				return distObj;
			} else {
				throw new RuntimeException("districts are not found for the Id:" + id);
			}
	}
	
	
	
	

	@GetMapping("/district")
	public List<DistrictMaster> get(){
		return districtDAO.findAll();
	}
	
	@GetMapping("/district/{id}")
	public DistrictMaster get(@PathVariable int id) {
		Optional<DistrictMaster> districtObj = districtDAO.findById(id);
		if(districtObj.isPresent()) {
			return districtObj.get();
		}else {
			throw new RuntimeException("district is not found for the Id:" + id);
		}
	}


	@PutMapping("/district")
	public String update(@RequestBody DistrictMasterDTO districtMasterdto) {
		
	      int stateId=districtMasterdto.getStateId();
         DistrictMaster dm=new DistrictMaster();
		      dm.setDistrictId(districtMasterdto.getDistrictId());
		      dm.setDistrictName(districtMasterdto.getDistrictName());
		      return stateDAO.findById(stateId).map(stateMaster -> {
		    	  dm.setStateMaster(stateMaster);
			//stateMaster.setCountryMaster(countryMaster);
			return SUCCESS;
		}).orElseThrow(() -> new ResourceNotFoundException("state Id " + stateId + " not found"));
		
		
		}
	
	@DeleteMapping("/district/{id}")
	public String delete(@PathVariable int id) {
		Optional<DistrictMaster> district = districtDAO.findById(id);
		if(district.isPresent()) {
			districtDAO.delete(district.get());
			return SUCCESS;
		}else {
			return FAILED;
		}
	}
	
}
