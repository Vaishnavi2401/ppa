package com.cdac.announcementservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cdac.announcementservice.models.Announcement;
import com.cdac.announcementservice.models.AnnouncementReadStatus;

public interface AnnouncementStatusRepository extends JpaRepository<AnnouncementReadStatus, String>, JpaSpecificationExecutor<AnnouncementReadStatus> {


}
