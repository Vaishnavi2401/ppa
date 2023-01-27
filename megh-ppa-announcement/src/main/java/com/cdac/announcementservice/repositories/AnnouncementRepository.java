package com.cdac.announcementservice.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdac.announcementservice.models.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer>, JpaSpecificationExecutor<Announcement> {

  public List<Announcement> findByCourseId(String id);
  
  public List<Announcement> findByCreatedBy(String id);
  
  public Optional<Announcement> findById(int id);
  
    @Query("select am from Announcement am where (:x between am.publihFrom and am.publishUpto) or (:y between am.publihFrom and am.publishUpto)")
    public List<Announcement> findByRange(@Param("x") Date  sdate,@Param("y") Date  edate);
   
    @Query("SELECT am  FROM Announcement am where am.type='1'  and am.createdBy=:x order by am.publihFrom desc")
	public List<Announcement> findAllGeneralAnnouncementByAuthor(@Param("x") String author);
    
    @Query("SELECT am  FROM Announcement am where am.type='2' and am.courseId=:y and am.createdBy=:x order by am.publihFrom desc")
	public List<Announcement> findAllCourseAnnouncementByAuthor(@Param("x") String author, @Param("y") String courseId);


    @Query("SELECT am  FROM Announcement am where am.type='1'  and am.createdBy=:x and (NOW() BETWEEN am.publihFrom and am.publishUpto) order by am.publihFrom desc")
   	public List<Announcement> findCurrentGeneralAnnouncementByAuthor(@Param("x") String author);
      
    @Query("SELECT am  FROM Announcement am where am.type='2' and am.courseId=:y and am.createdBy=:x and (NOW() BETWEEN am.publihFrom and am.publishUpto) order by am.publihFrom desc")
   	public List<Announcement> findCurrentCourseAnnouncementByAuthor(@Param("x") String author, @Param("y") String courseId);

    @Query("SELECT am FROM Announcement am WHERE (am.publihFrom <= NOW()) and (am.publishUpto >= NOW()) and (am.type='1')")
    List<Announcement> findByCurrentDatetoPublishupto();
  
}
