package com.cdac.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dao.CountryDAO;
import com.cdac.dao.StateDAO;
import com.cdac.exception.ResourceNotFoundException;
import com.cdac.model.CountryMaster;
import com.cdac.model.StateMaster;
import com.cdac.util.EmailSender;
import com.cdac.dto.StateMasterDTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/um_api")
public class StateController {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";	
	
	@Autowired
	private StateDAO stateDAO;

	@Autowired
	private CountryDAO countryDAO;
	
	@Autowired
	private EmailSender eMail;
	
	@PostMapping(value= "sendEmail")
	public String  sendEmail(@RequestParam String receipient, @RequestParam String subject, @RequestParam String bodytext) {
		
		
		try {
			
			eMail.sendEmail(receipient, subject, bodytext);
			
			
			
			return "Success";
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return "Failed";
		
	}
	
	@PostMapping("/state")
	public String save(@RequestBody StateMasterDTO stateMasterdto) {
		      int countryId=stateMasterdto.getCountryId();
             StateMaster sm=new StateMaster();
		      sm.setStateId(stateMasterdto.getStateId());
		      sm.setStateName(stateMasterdto.getStateName());
		      
		      return countryDAO.findById(countryId).map(countryMaster -> {
		    	  sm.setCountryMaster(countryMaster);
			//stateMaster.setCountryMaster(countryMaster);
			 stateDAO.save(sm);
			 return SUCCESS;
		}).orElseThrow(() -> new ResourceNotFoundException("country Id " + countryId + " not found"));

	}

	@GetMapping("/state")
	public List<StateMaster> get() {
		return stateDAO.findAll(Sort.by(Sort.Direction.ASC, "stateName"));

	}
	
	@GetMapping("/state/bycountry/{id}")
	public List<StateMaster> getStateByCountryId(@PathVariable int id) {
		
	CountryMaster cm =new CountryMaster();
	cm.setCountryId(id);
		List<StateMaster> stateObj = stateDAO.findBycountryMasterOrderByStateNameAsc(cm);
		if (!stateObj.isEmpty()) {
			return stateObj;
		} else {
			throw new RuntimeException("state is not found for the Id:" + id);
		}
	}
	
	
	

	@GetMapping("/state/{id}")
	public StateMaster get(@PathVariable int id) {
		Optional<StateMaster> stateObj = stateDAO.findById(id);
		if (stateObj.isPresent()) {
			return stateObj.get();
		} else {
			throw new RuntimeException("state is not found for the Id:" + id);
		}
	}

	@PutMapping("/state")
	public String update(@RequestBody StateMasterDTO stateMasterdto) {
	      int countryId=stateMasterdto.getCountryId();
       StateMaster sm=new StateMaster();
	      sm.setStateId(stateMasterdto.getStateId());
	      sm.setStateName(stateMasterdto.getStateName());
	      
	      return countryDAO.findById(countryId).map(countryMaster -> {
	    	  sm.setCountryMaster(countryMaster);
		//stateMaster.setCountryMaster(countryMaster);
		 stateDAO.save(sm);
		 return SUCCESS;
	}).orElseThrow(() -> new ResourceNotFoundException("country Id " + countryId + " not found"));

}

	@DeleteMapping("/state/{id}")
	public String delete(@PathVariable int id) {
		Optional<StateMaster> state = stateDAO.findById(id);
		if (state.isPresent()) {
			stateDAO.delete(state.get());
			return SUCCESS;
		} else {
			//throw new RuntimeException("state is not found for the id " + id);
			return FAILED;
		}
	}
}
