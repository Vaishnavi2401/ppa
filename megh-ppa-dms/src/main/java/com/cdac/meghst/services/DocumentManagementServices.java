package com.cdac.meghst.services;

import java.util.List;
import java.util.Optional;

import javax.swing.text.AbstractDocument.Content;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.meghst.dtos.ContentDetailDTO;
import com.cdac.meghst.dtos.DirDetailDTO;
import com.cdac.meghst.models.ContentDetail;
import com.cdac.meghst.models.DirDetail;
import com.cdac.meghst.repositories.ContentDetailInterface;
import com.cdac.meghst.repositories.ContentIDInterface;

public interface DocumentManagementServices {

	// Directory creation child and parent declarations
	void createWorkspace(DirDetailDTO dirDetails);

	String createChildWorkspace(DirDetailDTO dirDetails);

	List<?> getJsonDetails(String userId);

	String updateDirectory(DirDetailDTO dirDetails);

	String deleteDirectory(DirDetailDTO dirDetails);

	// Content file upload declarations
	String fileUpload(MultipartFile file, String userId, String folderName, String durationInMinutes,
			String contentName, String zipStatus);

	List<ContentDetailInterface> getContentDetails(String cid, String userId);

	String getSelectedFile(String userId, int contentId);

	void deleteContent(ContentDetail cdetailDto);

	void updateContent(ContentDetailDTO cdetailDto, ContentDetail cdetail);

	List<ContentIDInterface> directoryStatusCheck(String dirChildId);
}
