package com.cdac.meghst.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.meghst.models.ContentDetail;
import com.cdac.meghst.models.VersionMaster;
import java.util.List;

@Repository
public interface VersionMasterRepo extends JpaRepository<VersionMaster, Integer>{

	List<VersionMaster> findByContentDetail(ContentDetail cdetail);
}
