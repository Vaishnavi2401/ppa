package com.cdac.feedback.configs;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 

import org.springframework.stereotype.Component;     
import org.springframework.web.filter.OncePerRequestFilter; 

@Component
public class MethodFilter extends OncePerRequestFilter { 

    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
                    throws ServletException, IOException { 
    	
    	
        if (request.getMethod().equals("POST")|| request.getMethod().equals("GET")|| request.getMethod().equals("OPTIONS")|| request.getMethod().equals("DELETE")|| request.getMethod().equals("PUT")) {
        	filterChain.doFilter(request, response); 
            
        } else { 
        	response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } 
    }
} 