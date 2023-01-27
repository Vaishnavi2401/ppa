package com.cdac.meghst.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.meghst.dtos.ContentDetailDTO;
import com.cdac.meghst.dtos.ContentDetailIDDTO;
import com.cdac.meghst.dtos.DirDetailDTO;
import com.cdac.meghst.models.ContentDetail;
import com.cdac.meghst.models.DirDetail;
import com.cdac.meghst.repositories.ContentIDInterface;
import com.cdac.meghst.services.DocumentManagementServices;
import com.google.gson.Gson;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/dms")
@RestController
public class DirectoryController {

	@Autowired
	DocumentManagementServices dmsservice;

	@PostMapping(value = "/addRootDirectory")
	ResponseEntity<DirDetail> createRootDirectory(@RequestBody DirDetailDTO dirDetailDto) {
		try {

			dmsservice.createWorkspace(dirDetailDto);

			return ResponseEntity.status(HttpStatus.CREATED).build();

		} catch (Exception e) {

		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PostMapping(value = "/addChildDirectory")
	String createChildDirectory(@RequestBody DirDetailDTO dirDetailDto) {
		return dmsservice.createChildWorkspace(dirDetailDto);
	}

	@GetMapping(value = "/getDirectories/{userId}")
	String getUserDirectories(@PathVariable String userId) {
		return (dmsservice.getJsonDetails(userId).toString());
	}

	@PostMapping(value = "/updateDirectory")
	String updateRootDirectory(@RequestBody DirDetailDTO dirDetailDto) {
		return dmsservice.updateDirectory(dirDetailDto);
	}

	@PostMapping(value = "/deleteDirectory")
	String deleteDirectory(@RequestBody DirDetailDTO dirDetailDto) {
		return dmsservice.deleteDirectory(dirDetailDto);
	}

	@PostMapping(value = "/directoryStatusCheck/{dirChildId}")
	List<ContentDetailIDDTO> directoryStatusCheck(@PathVariable("dirChildId") String dirChildId) {
		List<ContentIDInterface> contentIdInterface = null;
		List<ContentDetailIDDTO> cdList = null;
		cdList = new ArrayList<>();
		ContentDetailIDDTO cdDTO = null;
		contentIdInterface = dmsservice.directoryStatusCheck(dirChildId);
		for (ContentIDInterface ci : contentIdInterface) {
			cdDTO = new ContentDetailIDDTO();
			cdDTO.setContentId(ci.getcontentId());
			cdList.add(cdDTO);
		}
		return cdList;
	}

}
