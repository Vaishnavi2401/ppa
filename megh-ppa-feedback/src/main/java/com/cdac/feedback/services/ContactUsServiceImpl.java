package com.cdac.feedback.services;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.feedback.dto.ContactUsDto;
import com.cdac.feedback.models.ContactMaster;
import com.cdac.feedback.repositories.ContactUsMasterRepo;


@Service
public class ContactUsServiceImpl implements ContactUsServices{

	
	@Autowired
	ContactUsMasterRepo contactUsRepo;
	
	
	
	
	@Override
	public ContactMaster addContactUs(ContactUsDto contactUsDto) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT);
		ContactMaster contactMaster = new ContactMaster();
		contactMaster.setCreateDate(new Date());
		modelMapper.map(contactUsDto, contactMaster);

		return contactUsRepo.save(contactMaster);
		
	}

	@Override
	public List<ContactMaster> getAllContactUsDetails() {
	
		return contactUsRepo.findAll();
	}

}
