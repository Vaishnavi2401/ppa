package com.cdac.announcementservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cdac.announcementservice.models.AnnouncementLog;



public interface AnnouncementLogRepository extends JpaRepository<AnnouncementLog, Integer>, JpaSpecificationExecutor<AnnouncementLog> {

}
