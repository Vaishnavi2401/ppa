package com.cdac.courseorg.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.courseorg.models.CourseSchedule;

public interface CourseScheduleRepo extends JpaRepository<CourseSchedule, Integer>{
	
	Optional<CourseSchedule> findByCourseId(int id);
}
