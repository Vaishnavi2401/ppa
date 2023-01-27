package com.cdac.paymentService.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cdac.paymentService.models.paymentDetails;
import com.cdac.paymentService.repositories.paymentDetailsRepository;

@Service
public class paymentDetailsService {

	@Autowired
	public paymentDetailsRepository repo;
	
	public paymentDetails getOrderId(paymentDetails pd) {
			repo.save(pd);
			int newOrderId = repo.getOrderId(pd.getUserId(), pd.getCourseId());
			System.out.println(newOrderId);
			return repo.getNewOrder(newOrderId);
	}
	
	public paymentDetails getByOrderId(long orderId){
		return repo.getByOrderId(orderId);
	}
	
	public paymentDetails getPaymentDetails(String userId, int courseId) {
		//System.out.println("data "+ userId +" "+courseId);
		String currentStatus = "paid";
		return repo.findByUserIdCourseIdAndStatus(userId, currentStatus ,courseId);
	}
	
}
