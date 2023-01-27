package com.cdac.paymentService.models;

import java.sql.Timestamp;
import javax.persistence.Column;
import org.springframework.validation.annotation.Validated;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "payment_details")
public class paymentDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private long orderId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "course_id")
	private int courseId;
	
	@Column(name = "razor_order_id")
	private String razorOrderId;
	
	private String status;
	
	@Column(name = "payment_id")
	private String paymentId;
	
	@Column(name = "Amount")
	private double fees;
	

	@Column(name = "transaction_date")
	private Timestamp transactionDate;
	
	@Column(name = "payment_signature")
	private String paymentSignature;
	
	//private int tenantId;
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getRazorOrderId() {
		return razorOrderId;
	}

	public void setRazorOrderId(String razorOrderId) {
		this.razorOrderId = razorOrderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getPaymentSignature() {
		return paymentSignature;
	}

	public void setPaymentSignature(String paymentSignature) {
		this.paymentSignature = paymentSignature;
	}

	

}
