package com.cdac.courseorg.services;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.courseorg.dtos.ContentDetailDTO;
import com.cdac.courseorg.dtos.CourseCategoryDTO;
import com.cdac.courseorg.dtos.CourseMasterDTO;
import com.cdac.courseorg.dtos.CourseScheduleDTO;
import com.cdac.courseorg.dtos.DirDetailsDTO;
import com.cdac.courseorg.dtos.TenantCourseDetailsDTO;
import com.cdac.courseorg.dtos.TenantCourseDetailsMapDTO;
import com.cdac.courseorg.dtos.UpdatedCourseMasterDTO;
import com.cdac.courseorg.feign.CourseCatalougeClient;

import com.cdac.courseorg.models.ContentDetail;
import com.cdac.courseorg.models.CourseCategory;
import com.cdac.courseorg.models.CourseMaster;
import com.cdac.courseorg.models.CourseSchedule;
import com.cdac.courseorg.repositories.ContentDetailRepo;
import com.cdac.courseorg.repositories.CourseCategoryRepo;
import com.cdac.courseorg.repositories.CourseMasterRepo;
import com.cdac.courseorg.repositories.CourseScheduleRepo;
import com.cdac.courseorg.repositories.DirDetailsRepo;
import com.cdac.courseorg.repositories.UpdatedCourseMasterInterface;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cdac.courseorg.models.DirDetail;

import com.cdac.courseorg.ValidateFileType;

@Service
public class CourseOrganizerServicesImp implements CourseOrganizerServices {

	@Autowired
	CourseCategoryRepo cCategoryRepo;

	@Autowired
	CourseMasterRepo cMasterRepo;

	@Autowired
	CourseScheduleRepo cScheduleRepo;

	@Autowired
	DirDetailsRepo dirDetailRepo;

	@Autowired
	ContentDetailRepo cDetailRepo;

	@Value("${extern.resoures.path}")
	private String storingDirectory;

	@Value("${extern.resources.Dir}")
	private String storingFolder;

	@Value("${extern.resources.main.Dir}")
	private String mainDir;

	@Value("${course_duration_unlimited}")
	private int course_unlimited_duration;

	@Autowired
	CourseCatalougeClient coursecatalougeclient;
	
	String parentPath = "";
	String pId = "";


	// course category API's start from here

	@Override
	public String addCourseCategory(CourseCategoryDTO categoryDto) {
		if (categoryDto.getCategoryName().isEmpty() || categoryDto.getCategory_description().isEmpty())
			return "Values can't be blank";
		if (categoryDto.getCategoryName().length() > 100 || categoryDto.getCategory_description().length() > 1000)
			return "Character size exceeded !! name must be less than 10 and description less than 100 characters";
		Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher nameMatcher = pattern.matcher(categoryDto.getCategoryName());
		// Matcher descriptionMatcher =
		// pattern.matcher(categoryDto.getCategory_description());
		boolean isNameContainsSpecialCharacter = nameMatcher.find();
		// boolean isDescriptionContainsSpecialCharacter = descriptionMatcher.find();
		if (isNameContainsSpecialCharacter)
			return "No special characters allowed";
		CourseCategory courseCategory = new CourseCategory();
		courseCategory.setCategoryName(categoryDto.getCategoryName());
		courseCategory.setCategory_description(categoryDto.getCategory_description());
		cCategoryRepo.save(courseCategory);
		
		return "Category saved successfully";
	}

	@Override
	public List<CourseCategory> getAllCourseCategories() {
		return cCategoryRepo.findAll();
	}

	@Override
	public String updateCategory(CourseCategoryDTO categoryDto) {
		CourseCategory category = cCategoryRepo.findById(categoryDto.getCategoryId()).get();
		category.setCategory_description(categoryDto.getCategory_description());
		category.setCategoryName(categoryDto.getCategoryName());
		cCategoryRepo.save(category);
		return "Category updated successfully!!!";
	}

	@Override
	public String deleteCategory(int categoryId) {
		List<CourseMaster> courseList = cMasterRepo.findAll();
		CourseCategory category = cCategoryRepo.findById(categoryId).get();
		for (CourseMaster iterator : courseList) {
			if (iterator.getCategoryId() == categoryId) {
				return "Please delete the courses related to " + category.getCategoryName() + " first!!";
			}
		}
		cCategoryRepo.delete(category);
		return "Category deleted successfully!!";
	}

	// course category API's ends here

	// course master API's start from here


	String oldFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);

	public boolean isValidDate(String pDate) {
		try {
			Date pDate1 = sdf1.parse(pDate);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String addCourseMaster(CourseMasterDTO cMasterDto, CourseScheduleDTO cScheduleDto, MultipartFile file,
			MultipartFile video, MultipartFile banner) {

		System.out.println(cScheduleDto);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date()).toString();
		String dateAfter = null;
		CourseMaster cMaster = new CourseMaster();
		CourseSchedule cSchedule = new CourseSchedule();
		String status = null;
		long unixTime = 0;
		String mediaType = null;
		String contentType = null;
		boolean fileMimeStatus = true;
		boolean fileSizeStatus = true;
		long size = 0;
		JSONObject obj = new JSONObject();
		DirDetail dDetail = new DirDetail();
		String fileName = null;

		String bannerName = null;
		String videoName = null;
		ArrayList<DirDetail> arr = new ArrayList<>(dirDetailRepo.findAll());
		
		
		ArrayList<DirDetail> arruser = new ArrayList<>(dirDetailRepo.findByLastModifiedBy(cMasterDto.getUserId()));
		
		
		

		String filePath = null;
		File fileDir = null;
		Calendar cal = null;
		char initialLetter = 'A';
		ObjectMapper mapper = null;
		int count = 1;
		String defaultFileName = storingDirectory + cMasterDto.getUserId() + File.separator + "DefaultImage"
				+ File.separator + "default.jpg";

		String defaultBannerFileName = storingDirectory + cMasterDto.getUserId() + File.separator + "DefaultImage"
				+ File.separator + "default.jpg";
		String defaultVideoFileName = null;
		String imagePath = null;
		String videoPath = null;
		String bannerPath = null;
		try {
			if (cMasterDto.getCourse_Type().isEmpty() || cMasterDto.getCourseName().isEmpty()
					|| cMasterDto.getInst_profile().isEmpty() || cMasterDto.getCourse_access_type().isEmpty())
				return status = "File can't be blank";

			if (cMasterDto.getDuration() > course_unlimited_duration || cMasterDto.getDuration() < 0)
				return status = "Duration exceeded !!";
			if (cMasterDto.getCourse_Type() == "paid" && cMasterDto.getCourse_Fee() == 0) {
				return status = "course fee is blank";
			}
			if (cMasterDto.getDuration() == 0 && cMasterDto.getCourse_access_type().equals("unlimited")) {
				cMasterDto.setDuration(course_unlimited_duration);
			}
			System.out.println("cMasterDto.getDuration()" + cMasterDto.getDuration());
			if (cMasterDto.getCategoryId() == 0)
				return status = "Category can't be blank";

//			if (Timestamp.valueOf(date).compareTo(Timestamp.valueOf(cScheduleDto.getPublishDate())) >= 0) {				
//				return "Enter a valid publish date!!";
//			}

			if (Timestamp.valueOf(cScheduleDto.getPublishDate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getCommencementDate())) > 0)
				return "Enter a valid commencement date!!";

			// checking the duration and adding the commencement date and comparing it with
			// the course closing date
			cal = Calendar.getInstance();
			cal.setTime(sdf.parse(cScheduleDto.getCommencementDate()));
			cal.add(Calendar.DAY_OF_MONTH, cMasterDto.getDuration());
			dateAfter = sdf.format(cal.getTime());
			System.out.println(dateAfter);
			if (!Timestamp.valueOf(dateAfter).equals(Timestamp.valueOf(dateAfter)))
				return "Enter a valid closing date!!";
			if (Timestamp.valueOf(cScheduleDto.getPublishDate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getEnrollSdate())) > 0)
				return "Enter a valid enrollment start date!!";
			if (Timestamp.valueOf(cScheduleDto.getCommencementDate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getEnrollSdate())) < 0)
				return "Enrollment start date must be less than commencement date!!";
			if (Timestamp.valueOf(dateAfter).compareTo(Timestamp.valueOf(cScheduleDto.getEnrollEdate())) < 0)
				return "Enrollment end date must be less than course closing date!!";
			if (Timestamp.valueOf(cScheduleDto.getPublishDate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getEnrollEdate())) > 0)
				return "Enrollment end date must be greater than publish date!!";
			if (Timestamp.valueOf(cScheduleDto.getEnrollSdate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getEnrollEdate())) >= 0)
				return "Enter a valid end date!!";

			// Inserting image
			unixTime = System.currentTimeMillis() / 1000L;
			if (file == null) {
				fileName = defaultFileName;

				imagePath = storingFolder + File.separator + "DefaultImage" + File.separator + "default.jpg";

			} else {
				Tika tika = new Tika();
				mediaType = tika.detect(file.getBytes());
				contentType = tika.detect(file.getContentType());
				fileMimeStatus = ValidateFileType.validateImageMimeType(mediaType) ? true : false;
				size = file.getSize() / 1024;
				fileName = unixTime + "." + FilenameUtils.getExtension(file.getOriginalFilename());
				System.out.println(mediaType + " " + contentType + " " + fileMimeStatus + " " + " " + size);

				if (!fileMimeStatus)

					return "Course Image Mime type is not matching!!";
				if (size > 100) {
					fileSizeStatus = false;
					return "Course Image File size exceeds!!";
				}
			}

			if (banner == null) {
				bannerName = defaultBannerFileName;
				bannerPath = storingFolder + File.separator + "DefaultImage" + File.separator + "banner.jpg";
			} else {
				Tika tika = new Tika();
				mediaType = tika.detect(banner.getBytes());
				contentType = tika.detect(banner.getContentType());
				fileMimeStatus = ValidateFileType.validateImageMimeType(mediaType) ? true : false;
				size = banner.getSize() / 1024;
				bannerName = unixTime + "_banner" + "." + FilenameUtils.getExtension(banner.getOriginalFilename());
				System.out.println(mediaType + " " + contentType + " " + fileMimeStatus + " " + " " + size);

				if (!fileMimeStatus)
					return "Banner Mime type is not matching!!";
				if (size > 200) {
					fileSizeStatus = false;
					return "Banner File size exceeds!!";
				}
			}

			if (video == null) {
				videoName = defaultVideoFileName;
			} else {
				Tika tika = new Tika();
				System.out.println(video.getBytes());
				mediaType = tika.detect(video.getBytes());
				contentType = tika.detect(video.getContentType());
				fileMimeStatus = ValidateFileType.validateVideoMimeType(mediaType) ? true : false;
				size = video.getSize() / 1024;
				System.out.println("SIZE" + size);
				System.out.println("video.getSize()" + video.getSize());
				videoName = unixTime + "_Intro_video" + "." + FilenameUtils.getExtension(video.getOriginalFilename());
				System.out.println(mediaType + " " + contentType + " " + fileMimeStatus + " " + " " + size);

				if (!fileMimeStatus)
					return "Video Mime type is not matching!!";
				if (size > 2048000) {
					fileSizeStatus = false;
					return "video size exceeds!!";
				}
			}


			boolean createStructureDirecory = false;
			if (arruser.size() == 0) {
				createStructureDirectory(fileDir, cMasterDto.getUserId());
				createStructureDirecory = true;
			} else {
				createStructureDirecory = true;
			}

			if (createStructureDirecory) {

				int[] dirIds = new int[arr.size()]; 
				synchronized (this) {
					for (int i = 0; i < arr.size(); i++) {						
						if (arr.get(i).getDirParentId().equals(arr.get(i).getDirChildId())) {
							dirIds[i] = Integer.parseInt(arr.get(i).getDirParentId().split("A")[1]);
							//System.out.println("Arr Parent Id----------" + arr.get(i).getDirParentId());
							//System.out.println("Arr child Id----------" + arr.get(i).getDirChildId());							
							//count++;
						}
					}
					if(arr.size() != 0) {
						count = Arrays.stream(dirIds).max().getAsInt() + 1;
					}
					
					//System.out.println("count----------" + count);

//					filePath = storingDirectory + File.separator + cMasterDto.getUserId() + File.separator
//							+ "CourseStructure" + File.separator + initialLetter + count;
//					System.out.println("==========" + filePath);
//					fileDir = new File(filePath);
//					if (!fileDir.exists()) {
//						if (fileDir.mkdirs()) {
//							System.out.println("Structure Created");
//						} else {
//							System.out.println("Not created");
//						}
//					} else {
//						System.out.println("Root Directory not created");
//					}

					System.out.println("A----------" + count);

					dDetail.setDirChildId("A" + count);
					dDetail.setDirParentId("A" + count);
					dDetail.setDirName(cMasterDto.getCourseName());
					dDetail.setDirPath(File.separator + "A" + count);
					dDetail.setLastModifiedBy(cMasterDto.getUserId());

//					dDetail.setJsonData(obj.toString());
					System.out.println(dDetail.getDirParentId());
					obj.put("key", dDetail.getDirParentId());
					obj.put("label", dDetail.getDirName());
					obj.put("ModifiedDate", date);
					obj.put("nodes", new JSONArray());
					obj.put("nodetype", "root");
					obj.put("id", "A" + count);
					obj.put("publishDate", cScheduleDto.getPublishDate());

					System.out.println("publishDate" + cScheduleDto.getPublishDate());

					dDetail = dirDetailRepo.save(dDetail);
				}
			}

			// Adding the details in course master
			cMaster.setCategoryId(cMasterDto.getCategoryId());
			cMaster.setCourseFee(cMasterDto.getCourse_Fee());
			cMaster.setCourseType(cMasterDto.getCourse_Type());
			cMaster.setCourseName(cMasterDto.getCourseName());
			cMaster.setDuration(cMasterDto.getDuration());

			cMaster.setCourse_access_type(cMasterDto.getCourse_access_type());

			cMaster.setPrerequisite(cMasterDto.getPrerequisite());
			cMaster.setStatus("C");
			cMaster.setIsScormCompliant(cMasterDto.getIsScormCompliant());
			cMaster.setGeneralDetails(cMasterDto.getGeneralDetails());

			cMaster.setObjective(cMasterDto.getObjective());
			cMaster.setUserId(cMasterDto.getUserId());
			cMaster.setInst_profile(cMasterDto.getInst_profile());
			cMaster.setFee_discount(cMasterDto.getFee_discount());

			cMaster.setCourseStructureJson(obj.toString());
			cMaster.setDirChildId(dDetail.getDirChildId());
			System.out.println(obj.toString());
			cMaster = cMasterRepo.save(cMaster);



			if (fileMimeStatus && fileSizeStatus) {
				File allFileDir = new File(storingDirectory + cMasterDto.getUserId() + File.separator + "Courses"
						+ File.separator + Integer.toString(cMaster.getCourseId()) + File.separator + "CourseImage");
				if (!allFileDir.exists()) {
					if (allFileDir.mkdirs()) {
						System.out.println("Course Images directory created !!");
					} else {
						System.out.println("Course Images directory not created !!");
					}
				} else {
					System.out.println("Directory already Exists!!");
				}
				if (!fileName.equals(defaultFileName)) {
					byte[] bytes = file.getBytes();
					Path path = Paths.get(storingDirectory + cMasterDto.getUserId() + File.separator + "Courses"
							+ File.separator + Integer.toString(cMaster.getCourseId()) + File.separator + "CourseImage"
							+ File.separator + fileName);
					imagePath = storingFolder + File.separator + cMasterDto.getUserId() + File.separator + "Courses"
							+ File.separator + Integer.toString(cMaster.getCourseId()) + File.separator + "CourseImage"
							+ File.separator + fileName;
					Files.write(path, bytes);
				}

				if (!bannerName.equals(defaultBannerFileName)) {
					byte[] bytes = banner.getBytes();
					Path path = Paths.get(storingDirectory + cMasterDto.getUserId() + File.separator + "Courses"
							+ File.separator + Integer.toString(cMaster.getCourseId()) + File.separator + "CourseImage"
							+ File.separator + bannerName);
					bannerPath = storingFolder + File.separator + cMasterDto.getUserId() + File.separator + "Courses"
							+ File.separator + Integer.toString(cMaster.getCourseId()) + File.separator + "CourseImage"
							+ File.separator + bannerName;
					Files.write(path, bytes);
				}
				if (videoName != defaultVideoFileName) {
					byte[] bytes = video.getBytes();
					Path path = Paths.get(storingDirectory + cMasterDto.getUserId() + File.separator + "Courses"
							+ File.separator + Integer.toString(cMaster.getCourseId()) + File.separator + "CourseImage"
							+ File.separator + videoName);
					videoPath = storingFolder + File.separator + cMasterDto.getUserId() + File.separator + "Courses"
							+ File.separator + Integer.toString(cMaster.getCourseId()) + File.separator + "CourseImage"
							+ File.separator + videoName;
					Files.write(path, bytes);
				}

			}
			// Creating json file structure
			filePath = storingDirectory + cMasterDto.getUserId() + File.separator + "Courses" + File.separator
					+ Integer.toString(cMaster.getCourseId()) + File.separator + "CourseStructure";
			fileDir = new File(filePath);
			if (!fileDir.exists()) {
				if (fileDir.mkdirs()) {
					System.out.println("Course structure created");
				} else {
					System.out.println("Course structure not created");
				}
			} else {
				System.out.println("Folder not created!!");
			}
			if (fileDir.exists()) {
				mapper = new ObjectMapper();
				mapper.writeValue((new File(
						fileDir.toString() + File.separator + Integer.toString(cMaster.getCourseId()) + ".json")),
						cMaster.getCourseStructureJson());
			}

			// Adding the details in course schedule
			if (cScheduleDto.getCommencementDate().isEmpty() || cScheduleDto.getEnrollEdate().isEmpty()
					|| cScheduleDto.getEnrollSdate().isEmpty() || cScheduleDto.getPublishDate().isEmpty())
				return status = "Dates can't be blank";
			System.out.println("====" + Timestamp.valueOf(cScheduleDto.getCommencementDate()));

			cSchedule.setCommencementDate(Timestamp.valueOf(cScheduleDto.getCommencementDate()));
			cSchedule.setCourseClosingDate(Timestamp.valueOf(dateAfter));
			cSchedule.setCourseId(cMaster.getCourseId());
			cSchedule.setEnrollEdate(Timestamp.valueOf(cScheduleDto.getEnrollEdate()));
			cSchedule.setEnrollSdate(Timestamp.valueOf(cScheduleDto.getEnrollSdate()));
			cSchedule.setPublishDate(Timestamp.valueOf(cScheduleDto.getPublishDate()));
			cSchedule.setUserId(cScheduleDto.getUserId());
			cSchedule.setImageUrl(imagePath);

			cSchedule.setBanner(bannerPath);
			cSchedule.setVideo(videoPath);
			cScheduleRepo.save(cSchedule);

			TenantCourseDetailsDTO tCoursedetailsDTO = new TenantCourseDetailsDTO();
			TenantCourseDetailsMapDTO tCoursedetailsMapDTO = new TenantCourseDetailsMapDTO();
			tCoursedetailsDTO.setCategoryId(cMasterDto.getCategoryId());
			tCoursedetailsDTO.setCategory(cCategoryRepo.findById(cMaster.getCategoryId()).get().getCategoryName());
			tCoursedetailsDTO.setCommencementDate(Timestamp.valueOf(cScheduleDto.getCommencementDate()));
			tCoursedetailsDTO.setCourseClosingDate(Timestamp.valueOf(dateAfter));
			tCoursedetailsDTO.setCourseDescription(cMasterDto.getGeneralDetails());
			tCoursedetailsDTO.setCourseName(cMasterDto.getCourseName());
			tCoursedetailsDTO.setCourse_Fee(cMasterDto.getCourse_Fee());
			tCoursedetailsDTO.setDuration(cMasterDto.getDuration());
			tCoursedetailsDTO.setCourse_Type(cMasterDto.getCourse_Type());
			tCoursedetailsDTO.setEnrollEdate(Timestamp.valueOf(cScheduleDto.getEnrollEdate()));
			tCoursedetailsDTO.setEnrollSdate(Timestamp.valueOf(cScheduleDto.getEnrollSdate()));
			tCoursedetailsDTO.setPublishDate(Timestamp.valueOf(cScheduleDto.getPublishDate()));
			tCoursedetailsDTO.setImageUrl(imagePath);
			tCoursedetailsDTO.setStatus("C");
			tCoursedetailsMapDTO.setTenantId(1);
			tCoursedetailsMapDTO.setCourseId(cMaster.getCourseId());
			tCoursedetailsDTO.setId(tCoursedetailsMapDTO);
			System.out.println("befor calling getCourseDetails");
			coursecatalougeclient.getCourseDetails(tCoursedetailsDTO, cMasterDto.getUserId());
			System.out.println("after calling getCourseDetails");
			return status = "Course Created Successfully !!";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public void createStructureDirectory(File file, String userId) {
		file = new File(storingDirectory + File.separator + userId);
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

	@Override

	public String updateCourseMaster(CourseMasterDTO cMasterDto, CourseScheduleDTO cScheduleDto, MultipartFile file,
			MultipartFile video, MultipartFile banner) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date()).toString();
		String dateAfter = null;
		CourseMaster courseMasterObj = cMasterRepo.findById(cMasterDto.getCourseId()).get();
		CourseSchedule courseScheduleObj = cScheduleRepo.findById(cMasterDto.getCourseId()).get();
		JSONObject jobj = new JSONObject(courseMasterObj.getCourseStructureJson());
		long unixTime = 0;
		String mediaType = null;
		String contentType = null;
		boolean fileMimeStatus = true;
		boolean fileSizeStatus = true;
		long size = 0;
		String fileName = null;
		String bannerName = null;
		String videoName = null;

		String status = null;
		Calendar cal = null;
		boolean defaultImageCheck = true;
		ObjectMapper objMapper = null;
//		String defaultFileName = storingDirectory + cMasterDto.getUserId() + File.separator + "DefaultImage"
//				+ File.separator + "default.jpg";
		String imagePath = null;

		String bannerPath = null;
		String videoPath = null;

		try {
			if (cMasterDto.getCourse_Type().isEmpty() || cMasterDto.getCourseName().isEmpty()
					|| cMasterDto.getInst_profile().isEmpty())
				return status = "Values can't be blank";
			if (cMasterDto.getDuration() > course_unlimited_duration || cMasterDto.getDuration() < 0)

				return status = "Duration exceeded !!";
			if (cMasterDto.getCategoryId() == 0)
				return status = "Category can't be blank";
			System.out.println("hi"
					+ (courseScheduleObj.getPublishDate()
							.compareTo(Timestamp.valueOf(cScheduleDto.getPublishDate())) < 0)
					+ courseScheduleObj.getPublishDate());

			System.out.println(cMasterDto.getGeneralDetails() + cMasterDto.getPrerequisite());
			if (cMasterDto.getDuration() == 0 && cMasterDto.getCourse_access_type().equals("unlimited")) {
				cMasterDto.setDuration(course_unlimited_duration);
			}
//			if (courseScheduleObj.getPublishDate().compareTo(Timestamp.valueOf(cScheduleDto.getPublishDate())) > 0)
//				return "Enter a valid publish date!!";

			if (Timestamp.valueOf(cScheduleDto.getPublishDate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getCommencementDate())) > 0)
				return "Enter a valid commencement date!!";

			// checking the duration and adding the commencement date and comparing it with
			// the course closing date
			cal = Calendar.getInstance();
			cal.setTime(sdf.parse(cScheduleDto.getCommencementDate()));
			cal.add(Calendar.DAY_OF_MONTH, cMasterDto.getDuration());
			dateAfter = sdf.format(cal.getTime());
			System.out.println(dateAfter);
			if (!Timestamp.valueOf(dateAfter).equals(Timestamp.valueOf(dateAfter)))
				return "Enter a valid closing date!!";
			if (Timestamp.valueOf(cScheduleDto.getPublishDate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getEnrollSdate())) > 0)
				return "Enter a valid enrollment start date!!";
			if (Timestamp.valueOf(cScheduleDto.getCommencementDate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getEnrollSdate())) < 0)
				return "Enrollment start date must be less than commencement date!!";
			if (Timestamp.valueOf(dateAfter).compareTo(Timestamp.valueOf(cScheduleDto.getEnrollEdate())) < 0)
				return "Enrollment end date must be less than course closing date!!";
			if (Timestamp.valueOf(cScheduleDto.getPublishDate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getEnrollEdate())) > 0)
				return "Enrollment end date must be greater than publish date!!";
			if (Timestamp.valueOf(cScheduleDto.getEnrollSdate())
					.compareTo(Timestamp.valueOf(cScheduleDto.getEnrollEdate())) >= 0)
				return "Enter a valid end date!!";

			// Updating image
			if (courseScheduleObj.getImageUrl().equals(cScheduleDto.getImageUrl())) {
				courseScheduleObj.setImageUrl(cScheduleDto.getImageUrl());
			} else {
				if (file == null) {
					if (!courseScheduleObj.getImageUrl().equals(null)) {
						imagePath = courseScheduleObj.getImageUrl();
					} else {
						imagePath = storingFolder + File.separator + courseMasterObj.getUserId() + File.separator
								+ "DefaultImage" + File.separator + "default.jpg";
						File delFile = new File(mainDir + File.separator + courseScheduleObj.getImageUrl());
						if (delFile.toString().substring(courseScheduleObj.getImageUrl().length() - 8,
								courseScheduleObj.getImageUrl().length() - 1).equals("default")) {
							System.out.println("Default file");
						} else {
							delFile.delete();
						}
					}

				} else {
					unixTime = System.currentTimeMillis() / 1000L;
					Tika tika = new Tika();
					mediaType = tika.detect(file.getBytes());
					contentType = tika.detect(file.getContentType());
					fileMimeStatus = ValidateFileType.validateImageMimeType(mediaType) ? true : false;
					size = file.getSize() / 1024;
					fileName = unixTime + "." + FilenameUtils.getExtension(file.getOriginalFilename());
					System.out.println(mediaType + " " + contentType + " " + fileMimeStatus + " " + " " + size);
					if (!fileMimeStatus)
						return "Mime type is not matching!!";
					if (size > 100) {
						fileSizeStatus = false;
						return "File size exceeds!!";
					}
					if (fileMimeStatus && fileSizeStatus) {
						File delFile = new File(mainDir + File.separator + courseScheduleObj.getImageUrl());
						System.out.println(delFile.toString().substring(courseScheduleObj.getImageUrl().length() - 8,
								courseScheduleObj.getImageUrl().length() - 1));
						byte[] bytes = file.getBytes();
						Path path = Paths.get(storingDirectory + courseMasterObj.getUserId() + File.separator
								+ "Courses" + File.separator + Integer.toString(courseMasterObj.getCourseId())
								+ File.separator + "CourseImage" + File.separator + fileName);

						imagePath = storingFolder + File.separator + courseMasterObj.getUserId() + File.separator
								+ "Courses" + File.separator + Integer.toString(courseMasterObj.getCourseId())
								+ File.separator + "CourseImage" + File.separator + fileName;
						if (delFile.toString().substring(courseScheduleObj.getImageUrl().length() - 8,
								courseScheduleObj.getImageUrl().length() - 1).equals("default")) {
							defaultImageCheck = false;

							Files.write(path, bytes);
						}
//					if (delFile.delete() && defaultImageCheck == true) {
//						Files.write(path, bytes);
//					}
						else {
							delFile.delete();
							Files.write(path, bytes);
						}
					}
				}
			}

			// Update Banner
			if (courseScheduleObj.getBanner().equals(cScheduleDto.getBanner())) {
				courseScheduleObj.setBanner(cScheduleDto.getBanner());
			} else {
				if (banner == null) {
					if (!courseScheduleObj.getBanner().equals(null)) {
						bannerPath = courseScheduleObj.getBanner();
					} else {
						bannerPath = storingFolder + File.separator + courseMasterObj.getUserId() + File.separator
								+ "DefaultImage" + File.separator + "banner.jpg";
						File delFile = new File(mainDir + File.separator + courseScheduleObj.getBanner());
						if (delFile.toString().substring(courseScheduleObj.getBanner().length() - 7,
								courseScheduleObj.getBanner().length() - 1).equals("banner")) {
							System.out.println("Default file");
						} else {
							delFile.delete();
						}
					}

				} else {
					unixTime = System.currentTimeMillis() / 1000L;
					Tika tika = new Tika();
					mediaType = tika.detect(banner.getBytes());
					contentType = tika.detect(banner.getContentType());
					fileMimeStatus = ValidateFileType.validateImageMimeType(mediaType) ? true : false;
					size = banner.getSize() / 1024;
					bannerName = unixTime + "_banner" + "." + FilenameUtils.getExtension(banner.getOriginalFilename());
					System.out.println(mediaType + " " + contentType + " " + fileMimeStatus + " " + " " + size);
					if (!fileMimeStatus)
						return "Banner Mime type is not matching!!";
					if (size > 200) {
						fileSizeStatus = false;
						return "Banner size exceeds!!";
					}
					if (fileMimeStatus && fileSizeStatus) {
						File delFile = new File(mainDir + File.separator + courseScheduleObj.getBanner());
						System.out.println(delFile.toString().substring(courseScheduleObj.getBanner().length() - 7,
								courseScheduleObj.getBanner().length() - 1));
						byte[] bytes = banner.getBytes();
						Path path = Paths.get(storingDirectory + courseMasterObj.getUserId() + File.separator
								+ "Courses" + File.separator + Integer.toString(courseMasterObj.getCourseId())
								+ File.separator + "CourseImage" + File.separator + bannerName);

						bannerPath = storingFolder + File.separator + courseMasterObj.getUserId() + File.separator
								+ "Courses" + File.separator + Integer.toString(courseMasterObj.getCourseId())
								+ File.separator + "CourseImage" + File.separator + bannerName;
						if (delFile.toString().substring(courseScheduleObj.getBanner().length() - 7,
								courseScheduleObj.getBanner().length() - 1).equals("banner")) {
							defaultImageCheck = false;

							Files.write(path, bytes);
						}
//					if (delFile.delete() && defaultImageCheck == true) {
//						Files.write(path, bytes);
//					}
						else {
							delFile.delete();


							Files.write(path, bytes);
						}
					}
				}
			}


			// update video
			if (courseScheduleObj.getVideo() == null) {
				courseScheduleObj.setVideo(cScheduleDto.getVideo());
			} else {
				if (video == null) {
					videoPath = courseScheduleObj.getVideo();
				} else {
					unixTime = System.currentTimeMillis() / 1000L;
					Tika tika = new Tika();
					System.out.println(video.getBytes());
					mediaType = tika.detect(video.getBytes());
					contentType = tika.detect(video.getContentType());
					fileMimeStatus = ValidateFileType.validateVideoMimeType(mediaType) ? true : false;
					size = video.getSize() / 1024;
					System.out.println("SIZE" + size);
					System.out.println("video.getSize()" + video.getSize());
					videoName = unixTime + "_Intro_video" + "."
							+ FilenameUtils.getExtension(video.getOriginalFilename());
					System.out.println(mediaType + " " + contentType + " " + fileMimeStatus + " " + " " + size);

					if (!fileMimeStatus)
						return "Video Mime type is not matching!!";
					if (size > 2048000) {
						fileSizeStatus = false;
						return "video size exceeds!!";
					}

					if (fileMimeStatus && fileSizeStatus) {
						File delFile = new File(mainDir + File.separator + courseScheduleObj.getVideo());
						System.out.println(delFile.toString().substring(courseScheduleObj.getVideo().length() - 7,
								courseScheduleObj.getVideo().length() - 1));
						byte[] bytes = video.getBytes();
						Path path = Paths.get(storingDirectory + courseMasterObj.getUserId() + File.separator
								+ "Courses" + File.separator + Integer.toString(courseMasterObj.getCourseId())
								+ File.separator + "CourseImage" + File.separator + videoName);

						videoPath = storingFolder + File.separator + courseMasterObj.getUserId() + File.separator
								+ "Courses" + File.separator + Integer.toString(courseMasterObj.getCourseId())
								+ File.separator + "CourseImage" + File.separator + videoName;
						if (delFile.toString().substring(courseScheduleObj.getVideo().length() - 7,
								courseScheduleObj.getVideo().length() - 1).equals("video")) {
							defaultImageCheck = false;
							Files.write(path, bytes);
						} else {
							delFile.delete();
							Files.write(path, bytes);
						}
					}
				}
			}


			// updating course structure
			jobj.put("label", cMasterDto.getCourseName());
			jobj.put("ModifiedDate", date);

			// Updating the details in course master
			courseMasterObj.setCategoryId(cMasterDto.getCategoryId());
			courseMasterObj.setCourseFee(cMasterDto.getCourse_Fee());
			courseMasterObj.setCourseType(cMasterDto.getCourse_Type());
			courseMasterObj.setCourseName(cMasterDto.getCourseName());
			courseMasterObj.setDuration(cMasterDto.getDuration());
			courseMasterObj.setCourse_access_type(cMasterDto.getCourse_access_type());
			courseMasterObj.setGeneralDetails(cMasterDto.getGeneralDetails());
			courseMasterObj.setIsScormCompliant(cMasterDto.getIsScormCompliant());
			courseMasterObj.setPrerequisite(cMasterDto.getPrerequisite());
			courseMasterObj.setObjective(cMasterDto.getObjective());
			courseMasterObj.setInst_profile(cMasterDto.getInst_profile());

			courseMasterObj.setStatus(courseMasterObj.getStatus());
			courseMasterObj.setCourseStructureJson(jobj.toString());
			cMasterRepo.save(courseMasterObj);

			// Updating the details in course_schedule
			if (cScheduleDto.getCommencementDate().isEmpty() || cScheduleDto.getEnrollEdate().isEmpty()
					|| cScheduleDto.getEnrollSdate().isEmpty() || cScheduleDto.getPublishDate().isEmpty())
				return status = "Dates can't be blank";
			courseScheduleObj.setCommencementDate(Timestamp.valueOf(cScheduleDto.getCommencementDate()));
			courseScheduleObj.setCourseClosingDate(Timestamp.valueOf(dateAfter));
			courseScheduleObj.setEnrollEdate(Timestamp.valueOf(cScheduleDto.getEnrollEdate()));
			courseScheduleObj.setEnrollSdate(Timestamp.valueOf(cScheduleDto.getEnrollSdate()));
			courseScheduleObj.setPublishDate(Timestamp.valueOf(cScheduleDto.getPublishDate()));
			courseScheduleObj.setImageUrl(imagePath);

			courseScheduleObj.setBanner(bannerPath);
			courseScheduleObj.setVideo(videoPath);
			cScheduleRepo.save(courseScheduleObj);

			TenantCourseDetailsDTO tCoursedetailsDTO = new TenantCourseDetailsDTO();
			TenantCourseDetailsMapDTO tCoursedetailsMapDTO = new TenantCourseDetailsMapDTO();
			tCoursedetailsDTO.setCategoryId(cMasterDto.getCategoryId());
			tCoursedetailsDTO
					.setCategory(cCategoryRepo.findById(courseMasterObj.getCategoryId()).get().getCategoryName());
			tCoursedetailsDTO.setCommencementDate(Timestamp.valueOf(cScheduleDto.getCommencementDate()));
			tCoursedetailsDTO.setCourseClosingDate(Timestamp.valueOf(dateAfter));
			tCoursedetailsDTO.setCourseDescription(cMasterDto.getGeneralDetails());
			tCoursedetailsDTO.setCourseName(cMasterDto.getCourseName());
			tCoursedetailsDTO.setCourse_Fee(cMasterDto.getCourse_Fee());
			tCoursedetailsDTO.setDuration(cMasterDto.getDuration());
			tCoursedetailsDTO.setCourse_Type(cMasterDto.getCourse_Type());
			tCoursedetailsDTO.setEnrollEdate(Timestamp.valueOf(cScheduleDto.getEnrollEdate()));
			tCoursedetailsDTO.setEnrollSdate(Timestamp.valueOf(cScheduleDto.getEnrollSdate()));
			tCoursedetailsDTO.setPublishDate(Timestamp.valueOf(cScheduleDto.getPublishDate()));
			tCoursedetailsDTO.setImageUrl(imagePath);
			tCoursedetailsDTO.setStatus(courseMasterObj.getStatus());
			tCoursedetailsMapDTO.setTenantId(1);
			tCoursedetailsMapDTO.setCourseId(courseMasterObj.getCourseId());
			tCoursedetailsDTO.setId(tCoursedetailsMapDTO);
			coursecatalougeclient.updateCourseDetails(tCoursedetailsDTO);


			// updating course structure file
			jobj.put("publishDate", courseScheduleObj.getPublishDate().toString());
			objMapper = new ObjectMapper();
			objMapper.writeValue(new File(storingDirectory + courseMasterObj.getUserId() + File.separator + "Courses"
					+ File.separator + Integer.toString(courseMasterObj.getCourseId()) + File.separator
					+ "CourseStructure" + File.separator + Integer.toString(courseMasterObj.getCourseId()) + ".json"),
					courseMasterObj.getCourseStructureJson());
			return status = "Course Updated Successfully !!";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public String deleteCourse(int courseId) {
		String FOLDER = null;
		File file = null;
		String fileStatus = null;
		List<String> listIds = new ArrayList<>();
		List<ContentDetail> contentList = cDetailRepo.findByCourseId(Integer.toString(courseId));
		System.out.println("Course id is" + courseId);
		CourseMaster cMasterObj = cMasterRepo.findById(courseId).get();
		System.out.println(cMasterObj.getCourseName());
		CourseSchedule cSchedule = cScheduleRepo.findByCourseId(cMasterObj.getCourseId()).get();
		JSONObject rootJson = new JSONObject(cMasterObj.getCourseStructureJson());
		DirDetail dDetail = dirDetailRepo
				.findByDirChildIdAndLastModifiedBy(cMasterObj.getDirChildId(), cMasterObj.getUserId()).get();
		boolean deleteBool = true;
// after integration of learner user management to check the enrolled users
//		if (cMasterObj.getStatus().equals("C")) {
//			if()
//		} else {
//
//		}
		if (deleteBool) {
			try {
//				FOLDER = mainDir + File.separator + storingFolder + File.separator + dDetail.getLastModifiedBy()
//						+ File.separator + "CourseStructure" + dDetail.getDirPath();
//				file = new File(FOLDER);
//				if (file.exists()) {
//					FileUtils.cleanDirectory(file);
//					file.delete();
//					fileStatus = "deleted successfully";
//				} else {
//					fileStatus = "not deleted";
//				}

				if (dDetail.getDirParentId().charAt(0) == 'A') {
					listIds.add(dDetail.getDirParentId());
					deleteChildDirectory(dDetail.getDirParentId(), listIds, rootJson.getJSONArray("nodes"));
					for (int i = 0; i < listIds.size(); i++) {
						dirDetailRepo.deleteParentDirectory(listIds.get(i));
						System.out.println("in parent id" + listIds.get(i));
					}
					fileStatus = "deleted successfully";
				} else {
					fileStatus = "Not deleted properly";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < contentList.size(); i++) {
				cDetailRepo.delete(contentList.get(i));
			}
			cScheduleRepo.deleteById(courseId);
			cMasterRepo.delete(cMasterObj);

		}
		return fileStatus;
	}

	@Override
	public List<UpdatedCourseMasterInterface> getAllCourses() {
		return cMasterRepo.findAllCourses();
	}

	@Override

	public List<UpdatedCourseMasterInterface> getCoursesByuserId(String userId) {
		return cMasterRepo.findCourseByuserId(userId);
	}

	@Override

	public List<UpdatedCourseMasterInterface> getAllPublishCourses() {
		return cMasterRepo.findAllPublishCourses();
	}

	@Override
	public UpdatedCourseMasterInterface getCourseById(int id) {
		return cMasterRepo.findCourseById(id);
	}

	@Override
	public List<UpdatedCourseMasterDTO> getActiveCourses() {
		List<UpdatedCourseMasterInterface> allCourseList = cMasterRepo.findAllCourses();
		List<UpdatedCourseMasterDTO> activeCourseList = null;
		UpdatedCourseMasterDTO activeCourseObj = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		String date = sdf.format(new Date()).toString();
		int timeStampStartVal = 0;
		int timeStampEndVal = 0;
		Timestamp ts1 = null;
		Timestamp ts2 = Timestamp.valueOf(date);
		Timestamp ts3 = null;
		activeCourseList = new ArrayList<>();
		for (UpdatedCourseMasterInterface i : allCourseList) {
			ts1 = Timestamp.valueOf(i.getEnrollStartDate());
			ts3 = Timestamp.valueOf(i.getEnrollEndDate());
			timeStampStartVal = ts1.compareTo(ts2);
			timeStampEndVal = ts3.compareTo(ts2);
			if (timeStampStartVal >= 0 && timeStampEndVal >= 0) {
				activeCourseObj = new UpdatedCourseMasterDTO();
				activeCourseObj.setCourseId(i.getCourseId());
				activeCourseObj.setCategoryName(i.getCategoryName());
				activeCourseObj.setCourseName(i.getCourseName());
				activeCourseObj.setDuration(i.getDuration());
				activeCourseObj.setGeneralDetails(i.getGeneralDetails());
				activeCourseObj.setPrerequisite(i.getPrerequisite());
				activeCourseObj.setCourseType(i.getCourseType());
				activeCourseObj.setCourseFee(i.getCourseFee());
				activeCourseObj.setPublishDate(i.getPublishDate());
				activeCourseObj.setEnrollStartDate(i.getEnrollStartDate());
				activeCourseObj.setEnrollEndDate(i.getEnrollEndDate());
				activeCourseObj.setCommenceDate(i.getCommenceDate());
				activeCourseObj.setClosingDate(i.getClosingDate());
				activeCourseObj.setImageUrl(i.getImageUrl());
				activeCourseList.add(activeCourseObj);
			}
		}
		return activeCourseList;
	}

	@Override
	public String addChildData(DirDetailsDTO dirDTo) {
		DirDetail dDetail = new DirDetail();
		String FOLDER = null;
		int courseId = 0;
		// DirDetail saveJsonObj = null;
		File fileChild = null;
		JSONObject obj = null;
		JSONObject jsonObj = null;
		JSONArray childJson = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		String date = sdf.format(new Date()).toString();
		ObjectMapper objMapper = new ObjectMapper();
		// String pid = null;
		char cId = (char) ((int) dirDTo.getDirParentId().charAt(0) + 1);
		if (dirDTo.getDirName().isEmpty())
			return "Directory name can't be blank";
		if (dirDetailRepo.findByDirChildIdAndLastModifiedBy(dirDTo.getDirParentId(), dirDTo.getLastModifiedBy())
				.isPresent()) {
			synchronized (this) {

				System.out.println("Child Count-----------"+cId);
				int childCount = dirDetailRepo.getChildCount(cId);
//				FOLDER = System.getProperty("user.dir") + File.separator + UPLOADED_FOLDER;
				if (childCount >= 0) {
					System.out.println("Inside if child count > 1  " + childCount);
					String parentPath = getParentPath(dirDTo.getDirParentId());
					dDetail.setDirChildId(cId + Integer.toString(childCount + 1));
					System.out.println("test --------------"+ cId + Integer.toString(childCount + 1));

					dDetail.setDirParentId(dirDTo.getDirParentId());
					dDetail.setDirName(dirDTo.getDirName());
					dDetail.setDirPath(
							File.separator + parentPath + File.separator + cId + Integer.toString(childCount + 1));
					dDetail.setLastModifiedBy(dirDTo.getLastModifiedBy());
					dDetail.setPublishDate(Timestamp.valueOf(dirDTo.getPublishDate()));
					System.out.println(cMasterRepo.getJsonData(pId));

					obj = new JSONObject(cMasterRepo.getJsonData(pId)); // root directory json
					courseId = Integer.parseInt(cMasterRepo.getCourseId(pId));
					// rootJSONObject = obj;
					System.out.println("Root Json obj" + obj.toString());
					System.out.println(dDetail.getDirParentId() + "- Parent ID of the current child id: "
							+ dDetail.getDirChildId());
					System.out.println("Uploaded folder" + storingDirectory);
					jsonObj = new JSONObject();
					jsonObj.put("key", dDetail.getDirChildId());
					jsonObj.put("id", dDetail.getDirChildId());
					jsonObj.put("label", dDetail.getDirName());
					jsonObj.put("ModifiedDate", date);
					jsonObj.put("nodes", new JSONArray());
					jsonObj.put("nodetype", "folder");

					jsonObj.put("isActive", true);

					jsonObj.put("publishDate", dDetail.getPublishDate());
					System.out.println("New Child Json " + jsonObj.toString());
					if (pId.equals(dDetail.getDirParentId())) {
						childJson = (JSONArray) obj.get("nodes");
						childJson.put(jsonObj);
						obj.put("nodes", childJson);

					} else {
						findAndUpdateChildJSON(dDetail.getDirParentId(), obj.getJSONArray("nodes"), jsonObj);
					}
					cMasterRepo.updateJsonDetails(obj.toString(), pId);
					System.out.println("Updated parent" + obj);
					dirDetailRepo.save(dDetail);
					fileChild = new File(storingDirectory + dDetail.getLastModifiedBy() + File.separator + "Courses"
							+ File.separator + Integer.toString(courseId) + File.separator + "CourseStructure"
							+ File.separator + Integer.toString(courseId) + ".json");
					try {
						objMapper.writeValue(fileChild, jsonObj.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
//					if (!fileChild.exists()) {
//						if (fileChild.mkdirs()) {
//							System.out.println("Child Structure created");
//						} else {
//							System.out.println("Child Structure not created");
//						}
//					} else {
//						System.out.println("Child file not created");
//					}

				}
			}
		} else {
			System.out.println("Invalid parent folder");
		}
		return obj.toString();
	}

	@Override
	public String addContent(List<ContentDetailDTO> contentDto) {
		CourseMaster courseMasterObj = cMasterRepo.findById(Integer.parseInt(contentDto.get(0).getCourseId())).get();
		JSONObject jobj = new JSONObject(courseMasterObj.getCourseStructureJson());
		JSONObject contentObj = null;
		ContentDetail cDetail = null;
		System.out.println(courseMasterObj.getCourseStructureJson());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		String date = sdf.format(new Date()).toString();
		JSONArray childJson = null;
		ObjectMapper objMapper = new ObjectMapper();
		for (int i = 0; i < contentDto.size(); i++) {
			// saving the content details values
			cDetail = new ContentDetail();
			cDetail.setCategoryType(contentDto.get(i).getCategoryType());
			cDetail.setContentType(contentDto.get(i).getContentType());
			cDetail.setCourseId(contentDto.get(i).getCourseId());
			cDetail.setDescription(contentDto.get(i).getDescription());
			cDetail.setFilepath(contentDto.get(i).getFilepath());
			cDetail.setCname(contentDto.get(i).getCname());
			cDetail.setItemId(contentDto.get(i).getItemId());
			cDetail.setTopicDuration(contentDto.get(i).getTopicDuration());
			cDetail.setUserId(contentDto.get(i).getUserId());

			cDetail.setpContentId(contentDto.get(i).getPContentId());

			cDetail.setPublishDate(Timestamp.valueOf(contentDto.get(i).getPublishDate()));
			cDetail = cDetailRepo.save(cDetail);

			// setting up the json values
			contentObj = new JSONObject();
			contentObj.put("key", Integer.toString(cDetail.getContentId()));
			contentObj.put("id", cDetail.getContentId());
			contentObj.put("ModifiedDate", date);
			contentObj.put("label", cDetail.getCname());
			contentObj.put("filePath", cDetail.getFilepath());
			contentObj.put("duration", cDetail.getTopicDuration());

			contentObj.put("nodes", new JSONArray());
			contentObj.put("nodetype", cDetail.getContentType());
			contentObj.put("publishDate", cDetail.getPublishDate());

			if (cDetail.getItemId().equals(jobj.get("key"))) {
				childJson = (JSONArray) jobj.get("nodes");
				childJson.put(contentObj);
				jobj.put("nodes", childJson);
			} else {
				findAndUpdateChildJSON(cDetail.getItemId(), jobj.getJSONArray("nodes"), contentObj);
				System.out.println("in else");
			}
			courseMasterObj.setCourseStructureJson(jobj.toString());
			try {
				objMapper
						.writeValue(
								new File(storingDirectory + courseMasterObj.getUserId() + File.separator + "Courses"
										+ File.separator + Integer.toString(courseMasterObj.getCourseId())
										+ File.separator + "CourseStructure" + File.separator
										+ Integer.toString(courseMasterObj.getCourseId()) + ".json"),
								courseMasterObj.getCourseStructureJson());
			} catch (Exception e) {
				e.printStackTrace();
			}
			cMasterRepo.save(courseMasterObj);
			System.out.println("Updated parent" + jobj);

		}
		return "Content added successfully!!";
	}

	private void findAndUpdateChildJSON(String pID, JSONArray childArray, JSONObject newChild) {
		String objvalue = null;
		JSONObject currentObj = null;
		Iterator<Object> iterator = childArray.iterator();
		while (iterator.hasNext()) {
			currentObj = (JSONObject) iterator.next();
			objvalue = (String) currentObj.get("key");
			childArray = currentObj.getJSONArray("nodes");
			if (objvalue.equals(pID)) {
				System.out.println("Child====" + childArray);
				childArray.put(newChild);
				break;
			} else {
				findAndUpdateChildJSON(pID, childArray, newChild);
			}
		}
		System.out.println(currentObj);
	}




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
	public String updateChildData(DirDetailsDTO dirDTo) {
		DirDetail dDetail = new DirDetail();
		CourseMaster cMaster = new CourseMaster();
		ObjectMapper objmapper = new ObjectMapper();
		getParentPath(dirDTo.getDirParentId());
		JSONObject jobj = new JSONObject(cMasterRepo.getJsonData(pId));
		int courseId = Integer.parseInt(cMasterRepo.getCourseId(pId));
		dDetail = dirDetailRepo.findByDirChildIdAndLastModifiedBy(dirDTo.getDirParentId(), dirDTo.getLastModifiedBy())
				.get();
		if (dirDTo.getDirParentId().charAt(0) == 'A') {
			dDetail.setDirName(dirDTo.getDirName());
			jobj.put("label", dirDTo.getDirName());
			cMaster.setCourseStructureJson(jobj.toString());
			dDetail = dirDetailRepo.save(dDetail);
		} else {
			findAndUpdateChildDirName(jobj.getJSONArray("nodes"), dirDTo.getDirParentId(), dirDTo.getDirName());
			dDetail.setDirName(dirDTo.getDirName());
			System.out.println(jobj.toString());
			cMasterRepo.updateJsonDetails(jobj.toString(), pId);
			dDetail = dirDetailRepo.save(dDetail);
		}
		try {
			objmapper.writeValue(new File(storingDirectory + dDetail.getLastModifiedBy() + File.separator + "Courses"
					+ File.separator + Integer.toString(courseId) + File.separator + "CourseStructure" + File.separator
					+ Integer.toString(courseId) + ".json"), jobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobj.toString();
	}

	private void findAndUpdateChildDirName(JSONArray childArray, String id, String updatedName) {
		JSONObject currentObj = null;
		System.out.println(currentObj);
		String objvalue = null;
		Iterator<Object> iterator = childArray.iterator();
		while (iterator.hasNext()) {
			currentObj = (JSONObject) iterator.next();
			objvalue = (String) currentObj.get("key");
			childArray = currentObj.getJSONArray("nodes");
			if (objvalue.equals(id)) {
				currentObj.put("label", updatedName);
				break;
			} else {
				findAndUpdateChildDirName(childArray, id, updatedName);
			}
		}
	}

	@Override
	public String deleteDirectory(DirDetailsDTO dirDTo) {
		String FOLDER = null;
		File file = null;
		String fileStatus = null;
		String path = null;
		getParentPath(dirDTo.getDirParentId());
		JSONObject rootJson = new JSONObject(cMasterRepo.getJsonData(pId));
		int courseId = Integer.parseInt(cMasterRepo.getCourseId(pId));
		List<String> listIds = new ArrayList();
		boolean childStatus = false;
		ObjectMapper objMapper = new ObjectMapper();
		try {
			path = dirDetailRepo.findByDirChildIdAndLastModifiedBy(dirDTo.getDirParentId(), dirDTo.getLastModifiedBy())
					.get().getDirPath();
			FOLDER = storingDirectory + File.separator + dirDTo.getLastModifiedBy() + File.separator + "CourseStructure"
					+ path;
			file = new File(FOLDER);
//			if (file.exists()) {
//				FileUtils.cleanDirectory(file);
//				file.delete();
//				fileStatus = "deleted successfully";
//			} else {
//				fileStatus = "not deleted";
//			}

			if (dirDTo.getDirParentId().charAt(0) == 'A') {
				listIds.add(dirDTo.getDirParentId());
				deleteChildDirectory(dirDTo.getDirParentId(), listIds, rootJson.getJSONArray("nodes"));
				for (int i = 0; i < listIds.size(); i++) {
					dirDetailRepo.deleteParentDirectory(listIds.get(i));
					System.out.println("in parent id" + listIds.get(i));
				}
			} else {
				if (dirDTo.getDirParentId().charAt(0) == 'B') {
					System.out.println("In else with B");
					for (int i = 0; i < rootJson.getJSONArray("nodes").length(); i++) {
						if (rootJson.getJSONArray("nodes").getJSONObject(i).get("key")
								.equals(dirDTo.getDirParentId())) {
							rootJson.getJSONArray("nodes").remove(i);
						}
					}
					childStatus = true;
				} else {
					deleteChildJson(dirDetailRepo.getParentId(dirDTo.getDirParentId()), rootJson.getJSONArray("nodes"),
							dirDTo.getDirParentId());
					childStatus = true;
				}
				if (childStatus == true) {
					cMasterRepo.updateJsonDetails(rootJson.toString(), pId);
					dirDetailRepo.deleteChildDirectory(dirDTo.getDirParentId().toString());
					listIds.add(dirDTo.getDirParentId());
					deleteChildDirectory(dirDTo.getDirParentId(), listIds, rootJson.getJSONArray("nodes"));
					for (int i = 0; i < listIds.size(); i++) {
						dirDetailRepo.deleteParentDirectory(listIds.get(i));
						System.out.println("in parent id" + listIds.get(i));
					}
				}
			}
			objMapper.writeValue(new File(storingDirectory + dirDTo.getLastModifiedBy() + File.separator + "Courses"
					+ File.separator + Integer.toString(courseId) + File.separator + "CourseStructure" + File.separator
					+ Integer.toString(courseId) + ".json"), rootJson.toString());
			fileStatus = "deleted successfully";
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
			objvalue = (String) currentObj.get("key");
			childArray = currentObj.getJSONArray("nodes");
			if (currentObj.getJSONArray("nodes") != null) {
				deleteChildDirectory(id, listIds, childArray);
				listIds.add(objvalue);
				break;
			} else {
				System.out.println("Iteration ended");
			}
		}

	}

	private void deleteChildJson(String pid, JSONArray childArray, String id) {
		System.out.println(pid);
		JSONObject currentObj = null;
		String objvalue = null;
		Iterator<Object> iterator = childArray.iterator();
		while (iterator.hasNext()) {
			currentObj = (JSONObject) iterator.next();
			objvalue = (String) currentObj.get("key");
			childArray = currentObj.getJSONArray("nodes");
			if (objvalue.equals(pid)) {
				for (int i = 0; i < childArray.length(); i++) {
					if (childArray.getJSONObject(i).get("key").equals(id)) {
						childArray.remove(i);
					}
				}
				break;
			} else {
				deleteChildJson(pid, childArray, id);
			}
		}
		System.out.println("Hello pranit" + currentObj);
	}

	@Override
	public String updateContent(ContentDetailDTO contentDto) {
		CourseMaster cMaster = cMasterRepo.findById(Integer.parseInt(contentDto.getCourseId())).get();
		JSONObject jobj = new JSONObject(cMaster.getCourseStructureJson());
		ContentDetail cDetail = cDetailRepo.findById(contentDto.getContentId()).get();
		ObjectMapper objMapper = new ObjectMapper();
		if (contentDto.getCname().isEmpty() && contentDto.getDescription().isEmpty())
			return "Values can't be blank";

		// updating the content values
		cDetail.setCname(contentDto.getCname());
		cDetail.setDescription(contentDto.getDescription());
		cDetailRepo.save(cDetail);
		// setting up the json value
		findAndUpdateChildDirName(jobj.getJSONArray("nodes"), Integer.toString(contentDto.getContentId()),
				contentDto.getCname());
		cMaster.setCourseStructureJson(jobj.toString());
		try {
			objMapper.writeValue(
					new File(storingDirectory + cMaster.getUserId() + File.separator + "Courses" + File.separator
							+ Integer.toString(cMaster.getCourseId()) + File.separator + "CourseStructure"
							+ File.separator + Integer.toString(cMaster.getCourseId()) + ".json"),
					cMaster.getCourseStructureJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
		cMasterRepo.save(cMaster);
		return jobj.toString();
	}

	@Override
	public String deleteContent(ContentDetailDTO contentDto) {
		CourseMaster cMaster = cMasterRepo.findById(Integer.parseInt(contentDto.getCourseId())).get();
		JSONObject jobj = new JSONObject(cMaster.getCourseStructureJson());
		ContentDetail cDetail = cDetailRepo.findById(contentDto.getContentId()).get();
		ObjectMapper objMapper = new ObjectMapper();
		// deleting the content if the value is in the root node
		if (cDetail.getItemId().charAt(0) == 'A') {
			System.out.println("in if");
			for (int i = 0; i < jobj.getJSONArray("nodes").length(); i++) {
				if (jobj.getJSONArray("nodes").getJSONObject(i).get("key")
						.equals(Integer.toString(cDetail.getContentId()))) {
					jobj.getJSONArray("nodes").remove(i);
				}
			}
			cMaster.setCourseStructureJson(jobj.toString());
		} else {
			// setting up the json values
			deleteChildJson(cDetail.getItemId(), jobj.getJSONArray("nodes"),
					Integer.toString(contentDto.getContentId()));
			System.out.println("In function" + jobj);
			cMaster.setCourseStructureJson(jobj.toString());
		}
		System.out.println("After deletion" + cMaster.getCourseStructureJson());
		try {
			objMapper.writeValue(
					new File(storingDirectory + cMaster.getUserId() + File.separator + "Courses" + File.separator
							+ Integer.toString(cMaster.getCourseId()) + File.separator + "CourseStructure"
							+ File.separator + Integer.toString(cMaster.getCourseId()) + ".json"),
					cMaster.getCourseStructureJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
		cMasterRepo.save(cMaster);
		// deleting the content from content details table
		cDetailRepo.delete(cDetail);
		return "Content deleted successfully !!";
	}

	@Override
	public boolean contentStatusCheck(int p_content_id) {
		List<ContentDetail> contentStatus = cDetailRepo.findBypContentId(p_content_id);
		if (contentStatus.size() >= 1) {
			return true;
		}
		return false;
	}

	@Override

	public String postPublishCourseStatus(int courseId) {
		try {
			CourseMaster courseMasterObj = cMasterRepo.findById(courseId).get();
			CourseSchedule courseScheduleObj = cScheduleRepo.findById(courseMasterObj.getCourseId()).get();
			courseMasterObj.setStatus("P");
			ObjectMapper objMapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
			String date = sdf.format(new Date()).toString();
			JSONObject jobj = null;
			File file = null;
			File publishedFile = null;
			File tempFile = null;
			file = new File(storingDirectory + courseMasterObj.getUserId() + File.separator
					+ "PublishedCourseStructureWorkspace" + File.separator
					+ Integer.toString(courseMasterObj.getCourseId()));
			if (!file.exists()) {
				if (file.mkdirs()) {

				} else {
					System.out.println("Folder not created !!");
				}
			} else {
				System.out.println("Folder already exists !!");
			}
			publishedFile = new File(storingDirectory + courseMasterObj.getUserId() + File.separator
					+ "PublishedCourseStructureWorkspace" + File.separator
					+ Integer.toString(courseMasterObj.getCourseId()) + File.separator
					+ Integer.toString(courseMasterObj.getCourseId()) + ".json");
			tempFile = new File(storingDirectory + courseMasterObj.getUserId() + File.separator
					+ "PublishedCourseStructureWorkspace" + File.separator
					+ Integer.toString(courseMasterObj.getCourseId()) + File.separator
					+ Integer.toString(courseMasterObj.getCourseId()) + "_" + Integer.toString(file.list().length)
					+ ".json");
			byte[] array = null;
			if (file.exists()) {
				if (publishedFile.exists()) {
					FileInputStream sourceFile = new FileInputStream(publishedFile);
					FileOutputStream destFile = new FileOutputStream(tempFile);

					array = sourceFile.readAllBytes();
					// reads all data from publishedFile
					sourceFile.read(array);

					// writes all data to newFile
					destFile.write(array);
					System.out.println("The input.txt file is copied to newFile.");

					// closes the stream
					sourceFile.close();
					destFile.close();

					objMapper.writeValue(new File(storingDirectory + courseMasterObj.getUserId() + File.separator
							+ "PublishedCourseStructureWorkspace" + File.separator + courseMasterObj.getCourseId()
							+ File.separator + courseMasterObj.getCourseId() + ".json"),
							courseMasterObj.getCourseStructureJson());
				} else {
					objMapper.writeValue(new File(storingDirectory + courseMasterObj.getUserId() + File.separator
							+ "PublishedCourseStructureWorkspace" + File.separator + courseMasterObj.getCourseId()
							+ File.separator + courseMasterObj.getCourseId() + ".json"),
							courseMasterObj.getCourseStructureJson());
				}
			}
			jobj = new JSONObject(courseMasterObj.getCourseStructureJson());
			jobj.put("lastPublishedDate", date);
			courseMasterObj.setCourseStructureJson(jobj.toString());
			storeMetadata(courseMasterObj, courseScheduleObj);
			cMasterRepo.save(courseMasterObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Course Published Successfully!!";
	}

	private void storeMetadata(CourseMaster cMaster, CourseSchedule cSchedule) {
		File metadataFile = new File(storingDirectory + cMaster.getUserId() + File.separator + "Courses"
				+ File.separator + Integer.toString(cMaster.getCourseId()) + File.separator + "CourseMetadata");
		File publishedFile = new File(storingDirectory + "PublishedCourses" + File.separator
				+ Integer.toString(cMaster.getCourseId()) + File.separator + "PublishedCourseMetadata");
		File courseStructureFile = new File(storingDirectory + "PublishedCourses" + File.separator
				+ Integer.toString(cMaster.getCourseId()) + File.separator + "PublishedCourseStructure");
		JSONObject metadataObject = new JSONObject();
		System.out.println(cMaster.getCourseName());
		ObjectMapper metadataMapper = new ObjectMapper();
		ObjectMapper publisedMapper = new ObjectMapper();
		ObjectMapper structureMapper = new ObjectMapper();
		if (!metadataFile.exists()) {
			if (metadataFile.mkdirs()) {
				System.out.println("File created successfully!!");
			} else {
				System.out.println("File not created");
			}
		} else {
			System.out.println("Directory already exist");
		}
		if (!publishedFile.exists()) {
			if (publishedFile.mkdirs()) {
				System.out.println("File created successfully!!");
			} else {
				System.out.println("File not created");
			}
		} else {
			System.out.println("Directory already exist");
		}
		if (!courseStructureFile.exists()) {
			if (courseStructureFile.mkdirs()) {
				System.out.println("File created successfully!!");
			} else {
				System.out.println("File not created");
			}
		} else {
			System.out.println("Directory already exist");
		}
		if (metadataFile.exists() && publishedFile.exists()) {
			metadataObject.put("courseId", cMaster.getCourseId());
			metadataObject.put("categoryName", cCategoryRepo.findById(cMaster.getCategoryId()).get().getCategoryName());
			metadataObject.put("courseName", cMaster.getCourseName());
			metadataObject.put("duration", cMaster.getDuration());

			metadataObject.put("constentAccessType", cMaster.getCourse_access_type());

			metadataObject.put("generalDetails", cMaster.getGeneralDetails());
			metadataObject.put("prerequisite", cMaster.getPrerequisite());
			metadataObject.put("courseType", cMaster.getCourseType());
			metadataObject.put("courseFee", cMaster.getCourseFee());
			metadataObject.put("objective", cMaster.getObjective());
			metadataObject.put("inst_profile", cMaster.getInst_profile());
			metadataObject.put("fee_discount", cMaster.getFee_discount());


			metadataObject.put("publishDate", cSchedule.getPublishDate());
			metadataObject.put("enrollmentStartDate", cSchedule.getEnrollSdate());
			metadataObject.put("enrollmentEndDate", cSchedule.getEnrollEdate());
			metadataObject.put("commencementDate", cSchedule.getCommencementDate());
			metadataObject.put("closingDate", cSchedule.getCourseClosingDate());
			metadataObject.put("imageUrl", cSchedule.getImageUrl());
			metadataObject.put("video", cSchedule.getVideo());
			metadataObject.put("banner", cSchedule.getBanner());

			try {
				metadataMapper
						.writeValue(
								new File(metadataFile.toString() + File.separator
										+ Integer.toString(cMaster.getCourseId()) + "_metadata.json"),
								metadataObject.toString());
				publisedMapper.writeValue(new File(
						publishedFile.toString() + File.separator + Integer.toString(cMaster.getCourseId()) + ".json"),
						metadataObject.toString());
				structureMapper
						.writeValue(
								new File(courseStructureFile.toString() + File.separator
										+ Integer.toString(cMaster.getCourseId()) + ".json"),
								cMaster.getCourseStructureJson());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getInstructorMetadata(int courseId) {
		String str = null;
		JSONObject jobj = null;
		try {
			Path fileName = Path.of(
					storingDirectory + "PublishedCourses" + File.separator + Integer.toString(courseId) + File.separator
							+ "PublishedCourseMetadata" + File.separator + Integer.toString(courseId) + ".json");
			// Now calling Files.readString() method to
			// read the file
			str = Files.readString(fileName);
//			str = str.replaceAll("\\\\", "");
//			str = str.substring(1, str.length() - 1);
			System.out.println(str);
//			jobj = new JSONObject(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "Course Metadata Not Available";
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return jobj.toString();
		return str;
	}

	@Override
	public String getInstructorStructure(int courseId) {
		String str = null;
		JSONObject jobj = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		String date = sdf.format(new Date()).toString();
		try {
			Path fileName = Path.of(
					storingDirectory + "PublishedCourses" + File.separator + Integer.toString(courseId) + File.separator
							+ "PublishedCourseStructure" + File.separator + Integer.toString(courseId) + ".json");
			// Now calling Files.readString() method to
			// read the file
			str = Files.readString(fileName);
			str = str.replaceAll("\\\\", "");
			str = str.substring(1, str.length() - 1);
			jobj = new JSONObject(str);
			if (Timestamp.valueOf(jobj.get("publishDate").toString()).compareTo(Timestamp.valueOf(date)) >= 0) {
				return "Course Structure is yet to publish";
			} else {
				findAndReplaceByPublishDate(jobj.getJSONArray("nodes"), date);
			}
			str = jobj.toString();
		} catch (Exception e) {
			//e.printStackTrace();
			return "700";
		} 
		/*
		 * catch (Exception e) { e.printStackTrace(); }
		 */

		return str;
	}

	private void findAndReplaceByPublishDate(JSONArray childArray, String currentDate) {
		String objvalue = null;
		JSONObject currentObj = null;
		Iterator<Object> iterator = childArray.iterator();
		String id = null;
		JSONArray subarray = null;
		while (iterator.hasNext()) {
			currentObj = (JSONObject) iterator.next();
			objvalue = (String) currentObj.get("publishDate");
			subarray = currentObj.getJSONArray("nodes");
			if (Timestamp.valueOf(objvalue).compareTo(Timestamp.valueOf(currentDate)) >= 0) {
				id = currentObj.getString("key");
				for (int i = 0; i < childArray.length(); i++) {
					if (childArray.getJSONObject(i).get("key").equals(id)) {
						childArray.remove(i);
					}
				}
				break;
			} else {
				findAndReplaceByPublishDate(subarray, currentDate);
			}
		}
	}

	@Override
	public String getSystemDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String date = sdf.format(new Date()).toString();
		return date;
	}

	@Override
	public String courseStatusUnPublish(int courseId) {
		try {
			CourseMaster courseMasterObj = cMasterRepo.findById(courseId).get();
			courseMasterObj.setStatus("U");
			cMasterRepo.save(courseMasterObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Course UnPublished Successfully!!";
	}

	@Override
	public String courseStatusDisable(int courseId) {
		try {
			CourseMaster courseMasterObj = cMasterRepo.findById(courseId).get();
			courseMasterObj.setStatus("D");
			cMasterRepo.save(courseMasterObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Course Disabled Successfully!!";

	}


}
