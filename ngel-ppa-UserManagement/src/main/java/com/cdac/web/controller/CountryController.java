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

import com.cdac.dao.CountryDAO;
import com.cdac.model.CountryMaster;

@CrossOrigin("*")
@RestController
@RequestMapping("/um_api")
public class CountryController {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";
	public static final String NOTFOUND = "Notfound";
	
	@Autowired
	private CountryDAO countryDAO;
	
	@PostMapping("/country")
	public String save(@RequestBody CountryMaster countryMaster) {
		try {
			countryDAO.save(countryMaster);
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
			
	}
	

	@GetMapping("/country")
	public List<CountryMaster> get(){
		return countryDAO.findAll();
	}
	
	@GetMapping("/country/{id}")
	public CountryMaster get(@PathVariable int id) {
		Optional<CountryMaster> countryObj = countryDAO.findById(id);
		if(countryObj.isPresent()) {
			return countryObj.get();
		}else {
			throw new RuntimeException("country is not found for the Id:" + id);
		}
	}


	@PutMapping("/country")
	public String update(@RequestBody CountryMaster countryObj) {
		try {
			countryDAO.save(countryObj);
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
		
	}
	
	@DeleteMapping("/country/{id}")
	public String delete(@PathVariable int id) {
		Optional<CountryMaster> country = countryDAO.findById(id);
		if(country.isPresent()) {
			countryDAO.delete(country.get());
			return SUCCESS;
		}else {
			return FAILED;
		}
	}
}
