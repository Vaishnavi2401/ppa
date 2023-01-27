package com.cdac.courseorg.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cdac.courseorg.models.ContentDetail;
import com.cdac.courseorg.dtos.ContentDetailDTO;
import com.cdac.courseorg.services.CourseOrganizerServices;

@CrossOrigin("*")
@RequestMapping(value = "/courseOrganizer")
@RestController
public class ContentDetailController {

	@Autowired
	CourseOrganizerServices services;

	@PostMapping(value = "/addContent")
	public String addContent(@RequestBody List<ContentDetailDTO> contentDto) {
		return services.addContent(contentDto);
	}

	@PostMapping(value = "/updateContent")
	public String updateContent(@RequestBody ContentDetailDTO contentDto) {
		return services.updateContent(contentDto);
	}

	@PostMapping(value = "/deleteContent")
	public String deleteContent(@RequestBody ContentDetailDTO contentDto) {
		return services.deleteContent(contentDto);
	} 

	@GetMapping(value = "/contentStatusCheck/{p_content_id}")
	public boolean contentStatusCheck(@PathVariable("p_content_id") int p_content_id) {
		return services.contentStatusCheck(p_content_id);

	}
}
