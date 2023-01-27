package com.cdac.paymentService.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.paymentService.models.paymentDetails;

@Repository
public interface paymentDetailsRepository extends JpaRepository<paymentDetails, Integer> {
	
	public paymentDetails findByUserIdAndCourseId(String userId, int courseId);
	public paymentDetails getByOrderId(long orderId);
	//public paymentDetails findByUserIdCourseIdAndStatus(String userId,  String status, int courseId);
	
	@Query(value = "SELECT * FROM payment_details pd where pd.user_id=:userId and pd.course_id=:courseId and pd.status=:status",nativeQuery = true)
	public paymentDetails findByUserIdCourseIdAndStatus(@Param("userId") String userId,@Param("status") String status, @Param("courseId") int courseId);
	//int getMaxsno(@Param("userId") String userId,  @Param("sessionId") String sessionId);

	@Query(value = "SELECT max(order_id) FROM payment_details pd where pd.user_id=:userId and pd.course_id=:courseId",nativeQuery = true)
	int getOrderId(@Param("userId") String userId, @Param("courseId") int courseId);
	
	@Query(value = "SELECT * FROM payment_details pd where pd.order_id=:orderId",nativeQuery = true)
	public paymentDetails getNewOrder(@Param("orderId") int orderId);
	
	@Query(value = "select * from payment_details pd where pd.course_id=:courseId and pd.transaction_date>=:fromdate and pd.transaction_date<=:todate and status = 'paid'", nativeQuery = true)
	public List<paymentDetails> findByCourseIdAndDateRange(@Param("courseId") String courseId, @Param("fromdate") Timestamp fromdate,@Param("todate") Timestamp todate);
}
