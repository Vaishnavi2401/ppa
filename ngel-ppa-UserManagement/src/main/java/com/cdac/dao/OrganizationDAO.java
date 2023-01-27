package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.model.OrgMaster;

public interface OrganizationDAO extends JpaRepository<OrgMaster, Integer>{

}
