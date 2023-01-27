package com.cdac.dao;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.model.CountryMaster;
import com.cdac.model.StateMaster;

@Repository
public interface StateDAO extends JpaRepository<StateMaster, Integer> {

	//Page<StateMaster> findByCountryId(int countryId, Pageable pageable);
    //Optional<StateMaster> findByStateIdAndCountryId(int stateid, int countryId);
	
	 List<StateMaster> findBycountryMasterOrderByStateNameAsc(CountryMaster countryMaster);
	 List<StateMaster> findByStateId(int stateId);
}
