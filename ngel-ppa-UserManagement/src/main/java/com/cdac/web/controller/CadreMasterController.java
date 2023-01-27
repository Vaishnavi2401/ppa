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

import com.cdac.dao.CadreDAO;
import com.cdac.model.CadreMaster;

@CrossOrigin("*")
@RestController
@RequestMapping("/um_api")
public class CadreMasterController {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";
	public static final String NOTFOUND = "Notfound";
	
	@Autowired
	private CadreDAO cadreDAO;
	
	@PostMapping("/cadre")
	public String save(@RequestBody CadreMaster cadreMaster) {
		try {
			cadreDAO.save(cadreMaster);
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
			
	}
	

	@GetMapping("/cadre")
	public List<CadreMaster> get(){
		return cadreDAO.findAll();
	}
	
	@GetMapping("/cadre/{id}")
	public CadreMaster get(@PathVariable int id) {
		Optional<CadreMaster> cadreObj = cadreDAO.findById(id);
		if(cadreObj.isPresent()) {
			return cadreObj.get();
		}else {
			throw new RuntimeException("cadre is not found for the Id:" + id);
		}
	}


	@PutMapping("/cadre")
	public String update(@RequestBody CadreMaster cadreObj) {
		try {
			cadreDAO.save(cadreObj);
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
		
	}
	
	@DeleteMapping("/cadre/{id}")
	public String delete(@PathVariable int id) {
		Optional<CadreMaster> cadre = cadreDAO.findById(id);
		if(cadre.isPresent()) {
			cadreDAO.delete(cadre.get());
			return SUCCESS;
		}else {
			return FAILED;
		}
	}
}
