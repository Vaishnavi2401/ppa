package com.cdac.meghst.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletContext;

import org.apache.tika.Tika;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.meghst.ValidateFileType;
import com.cdac.meghst.dtos.ContentDetailDTO;
import com.cdac.meghst.dtos.DirDetailDTO;
import com.cdac.meghst.models.ContentDetail;
import com.cdac.meghst.models.DirDetail;
import com.cdac.meghst.models.VersionMaster;
import com.cdac.meghst.repositories.ContentDetailInterface;
import com.cdac.meghst.repositories.ContentDetailRepo;
import com.cdac.meghst.repositories.ContentIDInterface;
import com.cdac.meghst.repositories.DirDetailRepo;
import com.cdac.meghst.repositories.VersionMasterRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

@Service
public class DocumentManagementServicesImp implements DocumentManagementServices {

	@Autowired
	DirDetailRepo dirDetailRepo;

	@Autowired
	ContentDetailRepo contentDetailRepo;

	@Autowired
	VersionMasterRepo versionMasterRepo;

	@Autowired
	ServletContext context;

//	@Value("${file.path}")
//	private String UPLOADED_FOLDER = "";

	@Value("${image.size}")
	private long IMAGE_SIZE;

	@Value("${doc.size}")
	private long DOC_SIZE;

	@Value("${video.size}")
	private long VIDEO_SIZE;

	@Value("${zip.size}")
	private long ZIP_SIZE;

	@Value("${html.size}")
	private long HTML_SIZE;

	@Value("${pdf.size}")
	private long PDF_SIZE;

	@Value("${text.size}")
	private long TEXT_SIZE;

	@Value("${virus.url}")
	private String VIRUS_URL;

	@Value("${check}")
	private boolean VIRUS_CHECK;

	@Value("${stream}")
	private String STREAM_CHECK;

	@Value("${extern.resoures.path}")
	private String UPLOADED_FOLDER = "";

	@Value("${extern.resources.Dir}")
	private String DIR_FOLDER = "";

	@Value("${extern.resources.main.Dir}")
	private String MAIN_DIR = "";

	@Value("${stream.api}")
	private String STREAM_API;

	// public JSONObject rootJSONObject;

	@Override
	public void createWorkspace(DirDetailDTO dirDetailDto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		String date = sdf.format(new Date()).toString();
		int count = 1;
		JSONObject obj = new JSONObject();
		DirDetail dDetail = new DirDetail();
		ArrayList<DirDetail> arr = new ArrayList<>(dirDetailRepo.findAll());
		ArrayList<DirDetail> arruser = new ArrayList<>(
				dirDetailRepo.findByLastModifiedBy(dirDetailDto.getLastModifiedBy()));
		String fileName = null;
		File file = null;
		char initialLetter = 'A';
//		String FOLDER = System.getProperty("user.dir") + File.separator + UPLOADED_FOLDER;
		System.out.println("==========123" + System.getProperty("user.dir"));
		boolean createStructureDirecory = false;
		if (arruser.size() == 0) {
			createStructureDirectory(file, dirDetailDto.getLastModifiedBy()); // for creating the directory if new user
																				// access dms for the first time
			createStructureDirecory = true;
		} else {
			createStructureDirecory = true;
		}
		if (createStructureDirecory) {
			int[] dirIds = new int[arr.size()]; 
			synchronized (this) {
				for (int i = 0; i < arr.size(); i++) {
					if (arr.get(i).getDirParentId().equals(arr.get(i).getDirChildId())) { // for getting the count of
							
						dirIds[i] = Integer.parseInt(arr.get(i).getDirParentId().split("A")[1]);// root directory
						//count++;
					}
				}
				if(arr.size() != 0) {
					count = Arrays.stream(dirIds).max().getAsInt() + 1;
				}
				
				fileName = UPLOADED_FOLDER + File.separator + dirDetailDto.getLastModifiedBy() + File.separator
						+ "DocumentManagement" + File.separator + initialLetter + count;
				System.out.println("==========" + fileName);
				file = new File(fileName);
				if (!file.exists()) {
					if (file.mkdirs()) {
						System.out.println("Structure Created");
					} else {
						System.out.println("Not created");
					}
				} else {
					System.out.println("Root Directory not created");
				}

				dDetail.setDirChildId("A" + count);
				dDetail.setDirParentId("A" + count);
				dDetail.setDirName(dirDetailDto.getDirName());
				dDetail.setDirPath(File.separator + "A" + count);
				dDetail.setLastModifiedBy(dirDetailDto.getLastModifiedBy());
//				dDetail.setJsonData(obj.toString());
				System.out.println(dDetail.getDirParentId());
				obj.put("Id", dDetail.getDirParentId());
				obj.put("Name", dDetail.getDirName());
				obj.put("ModifiedDate", date);
				obj.put("Child", new JSONArray());
				dDetail.setJsonData(obj.toString());
				dirDetailRepo.save(dDetail);
			}
		}
	}

	@Override
	public String createChildWorkspace(DirDetailDTO dirDetails) {
		DirDetail dDetail = new DirDetail();
		String FOLDER = null;
		// DirDetail saveJsonObj = null;
		File fileChild = null;
		JSONObject obj = null;
		JSONObject jsonObj = null;
		JSONArray childJson = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		String date = sdf.format(new Date()).toString();
		// String pid = null;
		char cId = (char) ((int) dirDetails.getDirParentId().charAt(0) + 1);

		if (dirDetailRepo.findByDirChildIdAndLastModifiedBy(dirDetails.getDirParentId(), dirDetails.getLastModifiedBy())
				.isPresent()) {
			synchronized (this) {
				
				//System.out.println("inside CID: "+ cId);
				int childCount = dirDetailRepo.getChildCount(cId);
				//System.out.println("inside childCount: "+ childCount);
//				FOLDER = System.getProperty("user.dir") + File.separator + UPLOADED_FOLDER;
				if (childCount >= 0) {
					//System.out.println("inside childCount: "+ childCount);
					String parentPath = getParentPath(dirDetails.getDirParentId());
					dDetail.setDirChildId(cId + Integer.toString(childCount + 1));
					dDetail.setDirParentId(dirDetails.getDirParentId());
					dDetail.setDirName(dirDetails.getDirName());
					dDetail.setDirPath(
							File.separator + parentPath + File.separator + cId + Integer.toString(childCount + 1));
					dDetail.setLastModifiedBy(dirDetails.getLastModifiedBy());
					System.out.println(dirDetailRepo.getJsonData(pId));

					obj = new JSONObject(dirDetailRepo.getJsonData(pId)); // root directory json
					// rootJSONObject = obj;
					System.out.println("Root Json obj" + obj.toString());
					System.out.println(dDetail.getDirParentId() + "- Parent ID of the current child id: "
							+ dDetail.getDirChildId());
					System.out.println("Uploaded folder" + UPLOADED_FOLDER);
					jsonObj = new JSONObject();
					jsonObj.put("Id", dDetail.getDirChildId());
					jsonObj.put("Name", dDetail.getDirName());
					jsonObj.put("ModifiedDate", date);
					jsonObj.put("Child", new JSONArray());
					System.out.println("New Child Json " + jsonObj.toString());

					// to check parent id equals to dirParent other wise find and update the json
					// data
					if (pId.equals(dDetail.getDirParentId())) {
						childJson = (JSONArray) obj.get("Child");
						childJson.put(jsonObj);
						obj.put("Child", childJson);

					} else {
						findAndUpdateChildJSON(dDetail.getDirParentId(), obj.getJSONArray("Child"), jsonObj);
					}
					dirDetailRepo.updateJsonDetails(obj.toString(), pId, pId);
					System.out.println("Updated parent" + obj);
					dirDetailRepo.save(dDetail);
					fileChild = new File(UPLOADED_FOLDER + File.separator + dDetail.getLastModifiedBy() + File.separator
							+ "DocumentManagement" + File.separator + dDetail.getDirPath());
					if (!fileChild.exists()) {
						if (fileChild.mkdirs()) {
							System.out.println("Child Structure created");
						} else {
							System.out.println("Child Structure not created");
						}
					} else {
						System.out.println("Child file not created");
					}

				}
			}
		} else {
			System.out.println("Invalid parent folder");
		}
		return obj.toString();
	}

	private void findAndUpdateChildJSON(String pID, JSONArray childArray, JSONObject newChild) {
		String objvalue = null;
		JSONObject currentObj = null;

		Iterator<Object> iterator = childArray.iterator();
		while (iterator.hasNext()) {
			currentObj = (JSONObject) iterator.next();
			objvalue = (String) currentObj.get("Id");
			childArray = currentObj.getJSONArray("Child");
			if (objvalue.equals(pID)) {
				//System.out.println("Child====" + childArray);
				childArray.put(newChild);
				break;
			} else {
				findAndUpdateChildJSON(pID, childArray, newChild);
			}
		}
	}

	String parentPath = "";
	String pId = "";

	public String getParentPath(String childId) {
		parentPath = childId;
		String parentId = dirDetailRepo.getParentId(childId);
		pId = parentId;
		if (parentId == null) {
			System.out.println("value empty");
		}
		if (!parentId.equals(childId)) {
			String temp = parentPath;
			parentPath = getParentPath(parentId) + File.separator + temp;
		} else {
			System.out.println("same parent");
		}
		return parentPath;
	}

	@Override
	public String fileUpload(MultipartFile file, String userId, String folderName, String durationInMinutes,
			String contentName, String zipStatus) {
		String fileStatus = "";
		try {
			String filePath = "";
			boolean fileMimeStatus;
			boolean fileContentStatus;
			boolean fileSizeStatus;
			boolean virusStatus;
			boolean addFile = false;
			String fileName = null;
			long unixTime = 0;
			String mediaType = null;
			String contentType = null;
			long size = 0;
			JSONObject virusJson = null;
			JSONArray jarr = null;
			ContentDetail contentDetail = null;
			ContentDetail versionContentDetail = null;
			VersionMaster versionMaster = null;
			DirDetail dDetail = null;
			ObjectMapper mapper = null;
			String tempPath = null;
			if (file.isEmpty()) {
				return "please upload the file";
			} else {
				if (!dirDetailRepo.findByDirChildIdAndLastModifiedBy(folderName, userId).isPresent()) {
					return "no such userId found or folder found";
				} else {
					for (int i = 0; i < dirDetailRepo.findByDirChildId(folderName).size(); i++) {
						filePath = dirDetailRepo.findByDirChildId(folderName).get(i).getDirPath();
					}
					unixTime = System.currentTimeMillis() / 1000L;
					Tika tika = new Tika();
					mediaType = tika.detect(file.getBytes());
					contentType = tika.detect(file.getContentType());
					size = file.getSize() / 1024;
					fileMimeStatus = ValidateFileType.validatePDFMimeType(mediaType) ? true
							: ValidateFileType.validateImageMimeType(mediaType) ? true
									: ValidateFileType.validateVideoMimeType(mediaType) ? true
											: ValidateFileType.validateDocMimeType(mediaType) ? true
													: ValidateFileType.validateHtmlMimeType(mediaType) ? true
															: ValidateFileType.validateTextMimeType(mediaType) ? true
																	: ValidateFileType.validateZIPMimeType(mediaType)
																			? true
																			: false;
					fileContentStatus = ValidateFileType.validatePDFMimeType(contentType) ? true
							: ValidateFileType.validateImageMimeType(contentType) ? true
									: ValidateFileType.validateVideoMimeType(contentType) ? true
											: ValidateFileType.validateDocMimeType(contentType) ? true
													: ValidateFileType.validateHtmlMimeType(contentType) ? true
															: ValidateFileType.validateTextMimeType(contentType) ? true
																	: ValidateFileType.validateZIPMimeType(contentType)
																			? true
																			: false;
					fileSizeStatus = checkFileSizeStatus(FilenameUtils.getExtension(file.getOriginalFilename()), size);
					System.out.println("Mime status" + fileMimeStatus + mediaType);
					System.out.println("Mime status" + fileContentStatus + contentType);
					System.out.println("Mime status" + fileSizeStatus + size);
					if (fileMimeStatus == false) {
						return "Mime type is not matching ..";
					}
					if (fileContentStatus == false) {
						return "Content type is not matching .. ";
					}
					if (fileSizeStatus == false) {
						return "File size exceeded";
					}
					fileName = unixTime + "." + FilenameUtils.getExtension(file.getOriginalFilename());
					// virus status checking
					if (VIRUS_CHECK == true) {
						virusJson = getVirusStatus(file);
						System.out.println(virusJson.get("data"));
						jarr = new JSONArray(virusJson.get("data").toString());
						virusJson = jarr.getJSONObject(0);
						virusStatus = Boolean.parseBoolean(virusJson.get("detected").toString());
						if (fileMimeStatus && fileContentStatus && fileSizeStatus && !virusStatus) {
							addFile = true;
						} else {
							fileStatus = "Malicious file detected";
						}
					}
					// virus status checking ends here
					else if (VIRUS_CHECK == false) {
						if (fileMimeStatus && fileContentStatus && fileSizeStatus) {
							addFile = true;
						}
					}
					System.out.println("add file status" + addFile);
					if (addFile) {
						byte[] bytes = file.getBytes();
						Path path = Paths.get(UPLOADED_FOLDER + File.separator + userId + File.separator
								+ "DocumentManagement" + File.separator + filePath + File.separator + fileName);
						// temp path is using for the check scrom file
						tempPath = DIR_FOLDER + File.separator + userId + File.separator + "DocumentManagement"
								+ File.separator + filePath + File.separator + fileName;
						String newfilePath = "DocumentManagement" + filePath;
						//System.out.println("file path------------" + newfilePath);
						Files.write(path, bytes);
						contentDetail = new ContentDetail();
						contentDetail.setUserId(userId);
						contentDetail.setContentType(FilenameUtils.getExtension(file.getOriginalFilename()));

						if (Boolean.parseBoolean(zipStatus)) {
							tempPath = unzipFile(userId, newfilePath, unixTime, path);
							contentDetail.setContentType("scorm");
						}

//						contentDetail.setContentName(
//								file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")));
						contentDetail.setContentName(contentName);
						dDetail = new DirDetail();
						dDetail.setDirChildId(folderName);
						contentDetail.setDirDetail(dDetail);
						contentDetail.setPreviewUrl(tempPath);
						contentDetail.setShareUrl(tempPath);
						contentDetail.setVirusScanStatus(1);
						contentDetail.setContentDuration(Integer.parseInt(durationInMinutes));
						if (Boolean.parseBoolean(STREAM_CHECK)) {
							System.out.println("In if");
							if (FilenameUtils.getExtension(file.getOriginalFilename()).equals("mp4")
									|| FilenameUtils.getExtension(file.getOriginalFilename()).equals("ogg")
									|| FilenameUtils.getExtension(file.getOriginalFilename()).equals("webm")) {
								contentDetail.setStreamingStatus("true");
								uploadToVideoStream(file, userId);
							}
						} else {
							contentDetail.setStreamingStatus("false");
						}
						contentDetail.setLastUpdatedBy(userId);
						contentDetail = contentDetailRepo.save(contentDetail);
						versionMaster = new VersionMaster();
						versionContentDetail = new ContentDetail();
						versionContentDetail.setContentId(contentDetail.getContentId());
						versionMaster.setContentDetail(versionContentDetail);
						versionMaster.setVersionChanges("Initial Change");
						versionMaster.setVersionNo(0);
						versionMasterRepo.save(versionMaster);
						mapper = new ObjectMapper();
						String jsonString = mapper.writeValueAsString(contentDetail);
//						return jsonString;
						fileStatus = "Uploaded Successfully";
					} else {
						fileStatus = "Failed";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";

		}
		return fileStatus;
	}

	private void uploadToVideoStream(MultipartFile file, String userId) {
		try {
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			RestTemplate template = new RestTemplate();
			map.add("file", file.getResource());
			map.add("email", userId);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
					map, headers);
			ResponseEntity<String> result = template.exchange(STREAM_API, HttpMethod.POST, requestEntity, String.class);
			System.out.println(result.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createStructureDirectory(File file, String userId) {
		file = new File(UPLOADED_FOLDER + File.separator + userId);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("structure directory created");
			} else {
				System.out.println("Failed");
			}
		} else {
			System.out.println("Not Created");
		}
	}

	private String unzipFile(String userId, String filePath, long unixTime, Path actualPath) {
		File scomDir = null;
		String tempPath = null;
		String zipFileName = null;
		scomDir = new File(UPLOADED_FOLDER + userId + File.separator + filePath + File.separator + "SCORM"
				+ File.separator + unixTime);
		if (!scomDir.exists()) {
			if (scomDir.mkdirs()) {
				System.out.println("Scom directory created");
			} else {
				System.out.println("Directory not created");
			}
		} else {
			System.out.println("Directory does not exist");
		}
		byte[] buffer = new byte[1024];
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(actualPath.toString()));
			ZipEntry zipEntry = zis.getNextEntry();
			zipFileName = zipEntry.getName();
			while (zipEntry != null) {
				String scomFilePath = scomDir + File.separator + zipEntry.getName();

				System.out.println("Unzipping " + scomFilePath);
				if (!zipEntry.isDirectory()) {
					FileOutputStream fos = new FileOutputStream(scomFilePath);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
				} else {
					File dir = new File(scomFilePath);
					dir.mkdir();
				}
				zis.closeEntry();
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			System.out.println("Unzipping complete");
			tempPath = DIR_FOLDER + File.separator + userId + File.separator + filePath + File.separator + "SCORM"
					+ File.separator + unixTime + File.separator + "index.html";
			System.out.println("Temp  Path" + tempPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempPath;
	}

	public boolean checkFileSizeStatus(String extention, long size) {
		boolean status;
		if ((extention.equals("jpeg") || extention.equals("png") || extention.equals("jpg")) && size <= IMAGE_SIZE) {
			status = true;
		} else if ((extention.equals("ogg") || extention.equals("webm") || extention.equals("mp4"))
				&& size <= VIDEO_SIZE) {
			status = true;
		} else if ((extention.equals("html") || extention.equals("htm")) && size <= HTML_SIZE) {
			status = true;
		} else if (extention.equals("pdf") && size <= PDF_SIZE) {
			status = true;
		} else if ((extention.equals("zip") || extention.equals("x-compressed-zip")
				|| extention.equals("x-7z-compressed") || extention.equals("octet-stream")) && size <= ZIP_SIZE) {
			status = true;
		} else if (extention.equals("txt") && size <= TEXT_SIZE) {
			status = true;
		} else if ((extention.equals("doc") || extention.equals("docx")) && size <= DOC_SIZE) {
			status = true;
		} else {
			System.out.println("t" + extention + "s" + size);
			status = false;
		}
		return status;
	}

	@Override
	public List<?> getJsonDetails(String userId) {
		return dirDetailRepo.getJsonDetails(userId);
	}

	private JSONObject getVirusStatus(MultipartFile file) {
		JSONObject obj = null;
		try {
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			RestTemplate template = new RestTemplate();
			map.add("files", file.getResource());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
					map, headers);
			ResponseEntity<String> result = template.exchange(VIRUS_URL, HttpMethod.POST, requestEntity, String.class);
			obj = new JSONObject(result.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public List<ContentDetailInterface> getContentDetails(String cid, String userId) {
		return contentDetailRepo.getContentDetails(cid, userId);
	}

	@Override
	public String getSelectedFile(String userId, int contentId) {
		String filePath = contentDetailRepo.findByContentId(contentId).getPreviewUrl();
		return filePath;
	}

	@Override
	public String updateDirectory(DirDetailDTO dirDetails) {
		DirDetail dDetail = new DirDetail();
		ObjectMapper objmapper = new ObjectMapper();
		String jsonstr = null;
		getParentPath(dirDetails.getDirParentId());
		JSONObject jobj = new JSONObject(dirDetailRepo.getJsonData(pId));
		dDetail = dirDetailRepo
				.findByDirChildIdAndLastModifiedBy(dirDetails.getDirParentId(), dirDetails.getLastModifiedBy()).get();
		if (dirDetails.getDirParentId().charAt(0) == 'A') {
			dDetail.setDirName(dirDetails.getDirName());
			jobj.put("Name", dirDetails.getDirName());
			dDetail.setJsonData(jobj.toString());
			dDetail = dirDetailRepo.save(dDetail);
		} else {
			findAndUpdateChildDirName(jobj.getJSONArray("Child"), dirDetails.getDirParentId(), dirDetails.getDirName());
			dDetail.setDirName(dirDetails.getDirName());
			System.out.println(jobj.toString());
			dirDetailRepo.updateJsonDetails(jobj.toString(), pId, pId);
			dDetail = dirDetailRepo.save(dDetail);
		}
		try {
			jsonstr = objmapper.writeValueAsString(dDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonstr;
	}

	private void findAndUpdateChildDirName(JSONArray childArray, String id, String updatedName) {
		JSONObject currentObj = null;
		String objvalue = null;
		Iterator<Object> iterator = childArray.iterator();
		while (iterator.hasNext()) {
			currentObj = (JSONObject) iterator.next();
			objvalue = (String) currentObj.get("Id");
			childArray = currentObj.getJSONArray("Child");
			if (objvalue.equals(id)) {
				currentObj.put("Name", updatedName);
				break;
			} else {
				findAndUpdateChildDirName(childArray, id, updatedName);
			}
		}
	}

	@Override
	public String deleteDirectory(DirDetailDTO dirDetails) {
		String FOLDER = null;
		File file = null;
		String fileStatus = null;
		String path = null;
		JSONObject rootJson = null;
		getParentPath(dirDetails.getDirParentId());
		rootJson = new JSONObject(dirDetailRepo.getJsonData(pId));
		List<String> listIds = new ArrayList();
		boolean childStatus = false;
		try {
			path = dirDetailRepo
					.findByDirChildIdAndLastModifiedBy(dirDetails.getDirParentId(), dirDetails.getLastModifiedBy())
					.get().getDirPath();
			FOLDER = UPLOADED_FOLDER + File.separator + dirDetails.getLastModifiedBy() + File.separator
					+ "DocumentManagement" + path;
			file = new File(FOLDER);
			if (file.exists()) {
				FileUtils.cleanDirectory(file);
				file.delete();
				fileStatus = "deleted successfully";
			} else {
				fileStatus = "not deleted";
			}
			if (fileStatus.equals("deleted successfully")) {
				if (dirDetails.getDirParentId().charAt(0) == 'A') {
					listIds.add(dirDetails.getDirParentId());
					deleteChildDirectory(dirDetails.getDirParentId(), listIds, rootJson.getJSONArray("Child"));
					for (int i = 0; i < listIds.size(); i++) {
						dirDetailRepo.deleteParentDirectory(listIds.get(i));
						System.out.println("in parent id" + listIds.get(i));
					}
				} else {
					if (dirDetails.getDirParentId().charAt(0) == 'B') {
						for (int i = 0; i < rootJson.getJSONArray("Child").length(); i++) {
							if (rootJson.getJSONArray("Child").getJSONObject(i).get("Id")
									.equals(dirDetails.getDirParentId())) {
								rootJson.getJSONArray("Child").remove(i);
							}
						}
						childStatus = true;
					} else {
						deleteChildJson(dirDetailRepo.getParentId(dirDetails.getDirParentId()),
								rootJson.getJSONArray("Child"), dirDetails.getDirParentId());
						childStatus = true;
					}
					if (childStatus == true) {
						dirDetailRepo.updateJsonDetails(rootJson.toString(), pId, pId);
						dirDetailRepo.deleteChildDirectory(dirDetails.getDirParentId().toString());
						listIds.add(dirDetails.getDirParentId());
						deleteChildDirectory(dirDetails.getDirParentId(), listIds, rootJson.getJSONArray("Child"));
						for (int i = 0; i < listIds.size(); i++) {
							dirDetailRepo.deleteParentDirectory(listIds.get(i));
							System.out.println("in parent id" + listIds.get(i));
						}
					}
				}
			} else {
				fileStatus = "Not deleted properly";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileStatus;
	}

	private void deleteChildDirectory(String id, List listIds, JSONArray childArray) {
		JSONObject currentObj = null;
		String objvalue = null;
		Iterator<Object> iterator = childArray.iterator();
		System.out.println("ChildArray" + childArray);
		while (iterator.hasNext()) {
			currentObj = (JSONObject) iterator.next();
			objvalue = (String) currentObj.get("Id");
			childArray = currentObj.getJSONArray("Child");
			if (currentObj.getJSONArray("Child") != null) {
				deleteChildDirectory(id, listIds, childArray);
				listIds.add(objvalue);
				break;
			} else {
				System.out.println("Iteration ended");
			}
		}

	}

	private void deleteChildJson(String pid, JSONArray childArray, String id) {
		JSONObject currentObj = null;
		String objvalue = null;
		Iterator<Object> iterator = childArray.iterator();
		while (iterator.hasNext()) {
			currentObj = (JSONObject) iterator.next();
			objvalue = (String) currentObj.get("Id");
			childArray = currentObj.getJSONArray("Child");
			if (objvalue.equals(pid)) {
				for (int i = 0; i < childArray.length(); i++) {
					if (childArray.getJSONObject(i).get("Id").equals(id)) {
						childArray.remove(i);
					}
				}
				break;
			} else {
				deleteChildJson(pid, childArray, id);
			}
		}
	}

	@Override
	public void deleteContent(ContentDetail cdetailDto) {
		String CONTENT_FOLDER = null;
		String scomDir = null;
		CONTENT_FOLDER = MAIN_DIR + File.separator + cdetailDto.getPreviewUrl();
		File scormFile = null;
		File scormZipfile = null;
		String scormDirName = null;
		try {
			File file = new File(CONTENT_FOLDER);
			if (cdetailDto.getContentType().equals("scorm")) {
				String scromFileURL = cdetailDto.getPreviewUrl();
				String[] s = scromFileURL.split("SCORM");
				String directoryName = s[1].substring(1, 11);
				scormFile = new File(
						MAIN_DIR + File.separator + s[0] + File.separator + "SCORM" + "\\/" + directoryName);
				scormZipfile = new File(MAIN_DIR + File.separator + s[0] + directoryName + ".zip");
				if (scormZipfile.delete()) {
					FileUtils.cleanDirectory(scormFile);
					scormFile.delete();
					System.out.println("File deleted successfully");
					contentDetailRepo.delete(cdetailDto);
				} else {
					System.out.println("File not deleted");
				}
				System.out.println(cdetailDto.getPreviewUrl().substring(0, cdetailDto.getPreviewUrl().length() - 11));
				System.out.println(
						cdetailDto.getPreviewUrl().substring(0, cdetailDto.getPreviewUrl().lastIndexOf("SCORM")));
				System.out.println(
						cdetailDto.getPreviewUrl().substring(cdetailDto.getPreviewUrl().lastIndexOf("SCORM") + 6,
								cdetailDto.getPreviewUrl().lastIndexOf("index.html") - 1));
			} else {
				if (file.delete()) {
					System.out.println("File deleted successfully");
					contentDetailRepo.delete(cdetailDto);
				} else {
					System.out.println("File not deleted");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateContent(ContentDetailDTO cdetailDto, ContentDetail cdetail) {
		List<VersionMaster> listVersionMaster = versionMasterRepo.findByContentDetail(cdetail);
		System.out.println(listVersionMaster.get(listVersionMaster.size() - 1).getVersionId());
		VersionMaster vmaster = new VersionMaster();
		if (cdetail.getContentName().equals(cdetailDto.getContentName())) {
			vmaster.setVersionChanges("Duration changed");
			cdetail.setContentDuration(cdetailDto.getContentDuration());
		} else if (cdetail.getContentDuration() == cdetailDto.getContentDuration()) {
			vmaster.setVersionChanges("Name changed");
			cdetail.setContentName(cdetailDto.getContentName());
		} else {
			vmaster.setVersionChanges("Name and duration changed");
			cdetail.setContentDuration(cdetailDto.getContentDuration());
			cdetail.setContentName(cdetailDto.getContentName());
		}
		vmaster.setContentDetail(cdetail);
		vmaster.setVersionNo(listVersionMaster.get(listVersionMaster.size() - 1).getVersionNo() + 1);
		versionMasterRepo.save(vmaster);
		contentDetailRepo.save(cdetail);

	}

	@Override
	public List<ContentIDInterface> directoryStatusCheck(String dirChildId) {
		return contentDetailRepo.findByDirChildId(dirChildId);

	}

}