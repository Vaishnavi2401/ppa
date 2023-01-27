package com.cdac.feedback.services;

import java.util.List;

import com.cdac.feedback.dto.ContactUsDto;
import com.cdac.feedback.models.ContactMaster;

public interface ContactUsServices {

	
	ContactMaster addContactUs(ContactUsDto contactUsDto);
	
	List<ContactMaster> getAllContactUsDetails(); 
	
}
