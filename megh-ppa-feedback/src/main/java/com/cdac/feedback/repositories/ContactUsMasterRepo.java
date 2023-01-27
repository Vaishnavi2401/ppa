package com.cdac.feedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.feedback.models.ContactMaster;


@Repository
public interface ContactUsMasterRepo extends JpaRepository<ContactMaster, Integer> {

	

}
