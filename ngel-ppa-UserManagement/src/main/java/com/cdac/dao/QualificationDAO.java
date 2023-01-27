package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.model.QualificationMaster;


@Repository
public interface QualificationDAO extends JpaRepository<QualificationMaster, Integer>{
	
	

}
