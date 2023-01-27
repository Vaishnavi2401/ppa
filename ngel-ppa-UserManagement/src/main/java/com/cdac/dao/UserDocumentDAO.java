package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.model.OrgMaster;
import com.cdac.model.UserDocument;

public interface UserDocumentDAO extends JpaRepository<UserDocument, String>{

}
