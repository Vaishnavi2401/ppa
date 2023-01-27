package com.cdac.feedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.feedback.models.TypeMaster;

@Repository
public interface TypeMasterRepo extends JpaRepository<TypeMaster, Integer>{

	
	TypeMaster findByTypeTitleIgnoreCase(String typeTitle);
}
