package com.cdac.feedback.configs;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandlingController implements ErrorController{

	
	 private  final Logger logger = LoggerFactory.getLogger(ErrorHandlingController.class);
	  
	@RequestMapping("/error")
	public ResponseEntity<Object> handleError(HttpServletRequest request) {
		
		
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	    	
	        Integer statusCode = Integer.valueOf(status.toString());
	        
	    
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				
				//logger.error("404 Error");
				
				 return new ResponseEntity<Object>(status,HttpStatus.NOT_FOUND);
				
			}  else if (statusCode == HttpStatus.UNAUTHORIZED.value() || statusCode == HttpStatus.FORBIDDEN.value()) {
				
				 return new ResponseEntity<Object>(status,HttpStatus.UNAUTHORIZED);
				
			}  else {

				 return new ResponseEntity<Object>(status,HttpStatus.REQUEST_TIMEOUT);
			}

	
	    }
	    return new ResponseEntity<Object>(status,HttpStatus.REQUEST_TIMEOUT);
	}

	@Override
	public String getErrorPath() {
	
		return null;
	}	
	

 
}