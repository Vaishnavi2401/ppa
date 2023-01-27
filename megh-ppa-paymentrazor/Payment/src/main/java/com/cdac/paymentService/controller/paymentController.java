package com.cdac.paymentService.controller;


import java.sql.Timestamp;

import javax.servlet.ServletContext;

import com.cdac.paymentService.utils.PGUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cdac.paymentService.models.paymentDetails;
import com.cdac.paymentService.service.paymentDetailsService;
import com.google.gson.*;

//import com.google.gson.Gson;




@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "payment")
public class paymentController {
	
	
	@Autowired
	private paymentDetailsService paymentDetailsService;
	@Autowired	
    private ServletContext context;
	
	@PostMapping("/saveNewDetails/{userId}/{courseId}/{tenantId}")
	public paymentDetails getOrderId(@PathVariable String userId, @PathVariable int courseId, @PathVariable int tenantId) {
		Timestamp timestamp=new Timestamp(System.currentTimeMillis());
		paymentDetails pd = new paymentDetails();
		pd.setCourseId(courseId);
		pd.setTransactionDate(timestamp);
		pd.setUserId(userId);
		pd.setStatus("new");
		
		String uri = "http://tmis1.hyderabad.cdac.in:8085/courses/getCourseByCourseIds?courseIds="+courseId+"&tenantId="+tenantId;
		
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    //JSONParser parser = new JSONParser();
	    //JSONObject json = new JSONObject(result);
	    //JSONObject json1 = json.getJSONObject("courseDetails");
	    JSONArray json = new JSONArray(result);
	    JSONObject objectInArray = json.getJSONObject(0);
	    pd.setFees(objectInArray.getDouble("fees"));
	   
	    //System.out.println("result------"+json);
	     
		paymentDetailsService.getOrderId(pd);
		return paymentDetailsService.getOrderId(pd);
		
	} 
	
	 // get payment details
 	
 	@GetMapping("/getPaymentDetails/{userId}/{courseId}/{tenantId}")
	public paymentDetails getPaymentDetails(@PathVariable String userId, @PathVariable int courseId, @PathVariable int tenantId) {   
 		try {
 			return paymentDetailsService.getPaymentDetails(userId, courseId);
 		}catch(Exception e) {			
 			e.printStackTrace();
 			return null;
 		}
		 
	    }
	
	//////////////
	
	 @PostMapping("/getOrderID/{fees}/{orderId}")
	public String getOrderID(@PathVariable int fees, @PathVariable long orderId) {    
		//System.out.println("context :: " + context.getServletContextName());
	       PGUtil pg = new PGUtil(context);
	       Gson gson = new Gson();  
	       if(fees <=0 ){
	           return null;
	       }else{
	    	   fees = fees * 100;
	       }
	        System.out.println("=====111111");
	       return gson.toJson(pg.generateOrderID(fees, orderId));
	    }
	
	 // update razor order id
	 
	 	@PutMapping("/updateOrderID/{orderId}/{razorOrderId}")
		public paymentDetails updateOrderID(@PathVariable long orderId, @PathVariable String razorOrderId) {   
	 		try {
	 			paymentDetails pd = paymentDetailsService.getByOrderId(orderId);
	 			pd.setStatus("in progress");
	 			pd.setRazorOrderId(razorOrderId);
	 			paymentDetailsService.getOrderId(pd);
	 			return paymentDetailsService.getOrderId(pd);
	 		}catch(Exception e) {
	 			
	 			e.printStackTrace();
	 			return null;
	 		}
			 
		    }
	
	 	
	 // update razorpay Details
		 
		 	@PutMapping("/updateRazorpayDetails/{orderId}/{paymentId}/{paymentSignature}")
			public paymentDetails updateRazorpayDetails(@PathVariable long orderId, @PathVariable String paymentId, @PathVariable String paymentSignature) {   
		 		try {
		 			Timestamp timestamp=new Timestamp(System.currentTimeMillis());
		 		    //Instant instanceNow3 = timestamp.toInstant();
		 		    //System.out.println(timestamp);
		 			paymentDetails pd = paymentDetailsService.getByOrderId(orderId);
		 			pd.setPaymentId(paymentId);
		 			pd.setPaymentSignature(paymentSignature);
		 			pd.setStatus("paid");
		 			pd.setTransactionDate(timestamp);
		 			paymentDetailsService.getOrderId(pd);
		 			return paymentDetailsService.getOrderId(pd);
		 		}catch(Exception e) {
		 			
		 			e.printStackTrace();
		 			return null;
		 		}
				 
			    }
	 
	 // to verify razor signature
	 
	 	@PostMapping(value = "/verifySignature/{paymentId}/{razorOrderId}/{paymentSignature}")
	    //@Produces(MediaType.TEXT_PLAIN)
	 	
	 	 public boolean verifySignature(@PathVariable String paymentId, @PathVariable String razorOrderId, 
	 			@PathVariable String paymentSignature) {
		       PGUtil pg = new PGUtil(context);
		       return pg.verifyPaymentSignature(paymentId, razorOrderId, paymentSignature);
		    }  
	 	
	    /*public boolean verifySignature(@QueryParam("razorpay_payment_id") String razorpay_payment_id, @QueryParam("razorpay_order_id") String razorpay_order_id, 
	            @QueryParam("razorpay_signature") String razorpay_signature) {
	       PGUtil pg = new PGUtil(context);
	       return pg.verifyPaymentSignature(razorpay_payment_id, razorpay_order_id, razorpay_signature);
	    } */ 
	 
	

}
