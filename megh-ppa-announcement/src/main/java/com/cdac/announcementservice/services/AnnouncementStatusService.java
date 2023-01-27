package com.cdac.announcementservice.services;

import org.springframework.stereotype.Service;

import com.cdac.announcementservice.models.AnnouncementReadStatus;
import com.cdac.announcementservice.repositories.AnnouncementLogRepository;
import com.cdac.announcementservice.repositories.AnnouncementRepository;
import com.cdac.announcementservice.repositories.AnnouncementStatusRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AnnouncementStatusService {
	private  AnnouncementStatusRepository repository1 ;;
	public AnnouncementReadStatus create(AnnouncementReadStatus announcement) {	
		return repository1.save(announcement);
	}
	

}
