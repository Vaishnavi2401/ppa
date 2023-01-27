package com.cdac.courseorg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.courseorg.models.PublishedCours;

public interface PublishCoursRepo extends JpaRepository<PublishedCours, Integer>{

}
