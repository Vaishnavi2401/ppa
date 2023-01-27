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

import com.cdac.dao.RankDAO;
import com.cdac.model.RankMaster;

@CrossOrigin("*")
@RestController
@RequestMapping("/um_api")
public class RankController {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";
	
	@Autowired
	private RankDAO rankDAO;
	
	@PostMapping("/rank")
	public String save(@RequestBody RankMaster rankMaster) {
		try {
			rankDAO.save(rankMaster);
			return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
		
		}
	

	@GetMapping("/rank")
	public List<RankMaster> get(){
		return rankDAO.findAll();
	}
	
	@GetMapping("/rank/{id}")
	public RankMaster get(@PathVariable int id) {
		Optional<RankMaster> rankObj = rankDAO.findById(id);
		if(rankObj.isPresent()) {
			return rankObj.get();
		}else {
			throw new RuntimeException("rank is not found for the Id:" + id);
		}
	}


	@PutMapping("/rank")
	public String update(@RequestBody RankMaster rankObj) {
		try {
			 rankDAO.save(rankObj);
			 return SUCCESS;
		} catch (Exception e) {
			return FAILED;
		}
	}
	
	@DeleteMapping("/rank/{id}")
	public String delete(@PathVariable int id) {
		Optional<RankMaster> rank = rankDAO.findById(id);
		if(rank.isPresent()) {
			rankDAO.delete(rank.get());
			return SUCCESS;
		}else {
			//throw new RuntimeException("rank is not found for the id "+id);
			return FAILED;
		}
	}
}
