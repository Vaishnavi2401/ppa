package com.cdac.announcementservice.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.announcementservice.models.Announcement;
import com.cdac.announcementservice.models.AnnouncementLog;
import com.cdac.announcementservice.models.ApplicationMaster;
import com.cdac.announcementservice.repositories.AnnouncementLogRepository;
import com.cdac.announcementservice.repositories.AnnouncementRepository;
import com.cdac.announcementservice.repositories.ApplicationMasterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

	private final AnnouncementRepository repository;
	private final AnnouncementLogRepository logRepository;
	private final ApplicationMasterRepository masterRepository;

	public ApplicationMaster create(ApplicationMaster appMaster) {
		masterRepository.createDB(appMaster.getAppHashIdentifier());
		masterRepository.createSchema(appMaster.getAppHashIdentifier());
		masterRepository.insertTenantInApplicationMaster(appMaster);
		return appMaster;
	}

	public List<Announcement> findAll() {
		return repository.findAll();
	}

	public List<Announcement> findAllByCourse(String id) {

		System.out.println("----------course id in service--" + id + "---" + repository.findByCourseId(id).size()
				+ "---" + repository.findByCourseId(id).toString());
		return repository.findByCourseId(id);
	}

	public List<Announcement> findAllByAuthor(String id) {
		return repository.findByCreatedBy(id);
	}

	public List<Announcement> findAllByRange(Date sdate, Date edate) {
		return repository.findByRange(sdate, edate);
	}

	public Optional<Announcement> findById(int id) {
		return repository.findById(id);
	}

	public Announcement create(Announcement announcement) {
		// logRepository.save(announcement);
		AnnouncementLog al = new AnnouncementLog();
		al.setBody(announcement.getBody());
		System.out.println(announcement.getPublihFrom());
		System.out.println(announcement.getPublishUpto());
		int id = repository.save(announcement).getId();
		System.out.println("---------id---" + id);
		al.setId(id);
		
		al.setPublihFrom(announcement.getPublihFrom());
		al.setPublishUpto(announcement.getPublishUpto());
		al.setTitle(announcement.getTitle());
		al.setUpdatedBy(announcement.getCreatedBy());
		logRepository.save(al);
		return announcement;
	}

	public Optional<Announcement> update(Announcement announcement, int id) {
		if (repository.findById(id).isPresent()) {
			AnnouncementLog al = new AnnouncementLog();
			al.setBody(announcement.getBody());
			al.setId(id);
			al.setPublihFrom(announcement.getPublihFrom());
			al.setPublishUpto(announcement.getPublishUpto());
			al.setTitle(announcement.getTitle());
			al.setUpdatedBy(announcement.getCreatedBy());
			logRepository.save(al);
			return Optional.of(repository.save(announcement));
		}
		return Optional.empty();
	}

	public Optional<Announcement> delete(int id) {
		Optional<Announcement> announcement = repository.findById(id);
		if (announcement.isPresent()) {

			repository.deleteById(id);
		}
		return announcement;
	}

	public List<Announcement> findAllGeneralAnnouncementByAuthor(String author) {

		return repository.findAllGeneralAnnouncementByAuthor(author);
	}

	public List<Announcement> findAllCourseAnnouncementByAuthor(String author, String courseId) {

		return repository.findAllCourseAnnouncementByAuthor(author, courseId);
	}

	public List<Announcement> findCurrentGeneralAnnouncementByAuthor(String author) {

		return repository.findCurrentGeneralAnnouncementByAuthor(author);
	}

	public List<Announcement> findCurrentCourseAnnouncementByAuthor(String author, String courseId) {

		return repository.findCurrentCourseAnnouncementByAuthor(author, courseId);
	}

	public List<Announcement> findByCurrentDateto() {
		return repository.findByCurrentDatetoPublishupto();
	}

}
