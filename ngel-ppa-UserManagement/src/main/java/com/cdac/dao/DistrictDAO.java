package com.cdac.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.model.DistrictMaster;
import com.cdac.model.StateMaster;

public interface DistrictDAO extends JpaRepository<DistrictMaster, Integer>{
	List<DistrictMaster> findBystateMaster(StateMaster stateMaster);
}
