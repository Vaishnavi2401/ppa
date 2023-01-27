package com.cdac.courseorg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.courseorg.models.CourseCategory;

public interface CourseCategoryRepo extends JpaRepository<CourseCategory, Integer>{

}
