package com.cdac.meghst.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cdac.meghst.dtos.ContentDetailDTO;
import com.cdac.meghst.models.ContentDetail;
import com.cdac.meghst.repositories.ContentDetailInterface;
import com.cdac.meghst.repositories.ContentDetailRepo;
import com.cdac.meghst.services.DocumentManagementServices;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/dms")
@RestController
public class ContentUploadController {

	@Autowired
	DocumentManagementServices dmServices;

	@Autowired
	ContentDetailRepo contentRepo;

//	@Value("${file.path}")
//	private String UPLOADED_FOLDER;
	
	@Value("${extern.resources.Dir}")
	private String UPLOADED_FOLDER;

	@PostMapping(value = "/fileUpload")
	public String fileUpload(@RequestPart MultipartFile file, @RequestPart("user_id") String userId,
			@RequestPart("dir_name") String dirName, @RequestPart("durationInMinutes") String durationInMinutes,@RequestPart("contentName") String contentName,@RequestPart("zipStatus") String zipStatus) {
		return dmServices.fileUpload(file, userId, dirName, durationInMinutes,contentName,zipStatus);
	}

	@GetMapping(value = "/getContentDetails/{id}/{userId}")
	public List<ContentDetailDTO> getContentDetails(@PathVariable String id, @PathVariable String userId,
			HttpServletRequest request) {
		List<ContentDetailInterface> contentDetails = null;
		List<ContentDetailDTO> cdsList = null;
		ContentDetailDTO cdto = null;
		String projectDir = null;
		String previewURL = null;
		contentDetails = dmServices.getContentDetails(id, userId);
		cdsList = new ArrayList<>();
		projectDir = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		for (ContentDetailInterface cdi : contentDetails) {
			cdto = new ContentDetailDTO();
			cdto.setContentId(cdi.getContent());
			cdto.setContentName(cdi.getName());
			cdto.setContentType(cdi.getType());
			cdto.setContentDuration(cdi.getDuration());
			previewURL = ServletUriComponentsBuilder.fromCurrentContextPath().path(projectDir + "/")
					.path(UPLOADED_FOLDER).path("/" + userId).path("/" + cdi.getContent()).toUriString();
			cdto.setPreviewUrl(previewURL);
			cdsList.add(cdto);

		}
		return cdsList;
	}

	@GetMapping(value = "/uploads/{userId}/{contentId}")
	@ResponseBody
	public String getSelectedFile(@PathVariable String userId, @PathVariable int contentId) {
		return dmServices.getSelectedFile(userId, contentId);
	}
	
	@PostMapping(value = "/updateContent")
	public ResponseEntity<ContentDetail> updateContent(@RequestBody ContentDetailDTO contentDto){
		Optional<ContentDetail> cDetail = contentRepo.findById(contentDto.getContentId());
		if(!cDetail.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		try {
			dmServices.updateContent(contentDto,cDetail.get());
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PostMapping(value = "/deleteContent/{contentId}")
	ResponseEntity<ContentDetail> deleteContentFile(@PathVariable int contentId) {
		Optional<ContentDetail> contentDetail = contentRepo.findById(contentId);
		if (!contentDetail.isPresent()) {

			return ResponseEntity.noContent().build();
		}
		try {
			dmServices.deleteContent(contentDetail.get());
			
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

}
