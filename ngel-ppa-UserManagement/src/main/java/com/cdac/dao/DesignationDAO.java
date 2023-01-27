package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.model.DesignationMaster;


@Repository
public interface DesignationDAO extends JpaRepository<DesignationMaster, Integer>{
	
	

}
