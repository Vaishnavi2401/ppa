package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.model.CadreMaster;


@Repository
public interface CadreDAO extends JpaRepository<CadreMaster, Integer>{
	
	

}
