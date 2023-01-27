package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.model.RankMaster;

public interface RankDAO extends JpaRepository<RankMaster, Integer> {

}
