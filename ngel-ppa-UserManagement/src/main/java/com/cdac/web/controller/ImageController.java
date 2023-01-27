package com.cdac.web.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.dao.LearnerDAO;
import com.cdac.model.LearnerMaster;
import com.cdac.util.FileService;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

@CrossOrigin("*")
@RestController
@RequestMapping("/um_api")
@Configuration
@PropertySource("classpath:application.properties")
public class ImageController {

	@Autowired
	LearnerDAO learnerDAO;

	@Value("${imagefolder.path}")
	private String UPLOADED_FOLDER;
	// Save the uploaded file to this folder

	// private static String UPLOADED_FOLDER = "C://tmp//";
	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";

	// @PostMapping(value="/imageupload",consumes = {"multipart/mixed"}) // //new
	// annotation since 4.3
	@PostMapping("/imageupload") // //new annotation since 4.3
	public String singleFileUpload(@RequestPart("file") MultipartFile file, @RequestPart("user_id") String user_id) {

		try {
			if (file.isEmpty()) {
				return "plase select image to upload";
			} else {
				List<LearnerMaster> lm = learnerDAO.findByLearnerUsername(user_id);
				if (lm.size() == 0) {
					return "No learner found with this id";
				} else {
					Tika tika = new Tika();
					String mediaType = tika.detect(file.getBytes());
					System.out.println(mediaType);
					String dirctorypath = UPLOADED_FOLDER + File.separator + user_id;
					File f = new File(dirctorypath);
					if (mediaType.equalsIgnoreCase("image/jpeg") || mediaType.equalsIgnoreCase("image/png")
							|| mediaType.equalsIgnoreCase("image/jpg")) {

						if (!(f.exists() && f.isDirectory())) {

							File source = new File(UPLOADED_FOLDER + "user.png");
							File dest = new File(dirctorypath);
							boolean flag = dest.mkdir();
							if (flag) {
								System.out.println("Directory created successfully");
							} else {
								System.out.println("Sorry couldnt create specified directory");
							}

						}

						byte[] bytes = file.getBytes();
						Path path = Paths.get(dirctorypath + File.separator + "user.png");
						Files.write(path, bytes);

						return "Image successfully uploaded";
					} else {
						return "Please select valid image";

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}

	}

	// @PostMapping("/fileupload") // //new annotation since 4.3
	public String FileUpload(@RequestPart("file1") MultipartFile profilepic,
			@RequestPart("file2") MultipartFile idproof, @RequestPart("user_id") String user_id) {

		try {
			if (profilepic.isEmpty() && idproof.isEmpty()) {
				return "invalidfiles";
			} else {
				List<LearnerMaster> lm = learnerDAO.findByEmail(user_id);
				if (lm.size() == 0) {
					return "invaliduserid";
				} else {
					Tika tika = new Tika();
					String mediaType = tika.detect(profilepic.getBytes());
					String mediaType1 = tika.detect(idproof.getBytes());
					System.out.println(mediaType);

					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] hashtext = md.digest(user_id.getBytes());
					BigInteger no = new BigInteger(1, hashtext);
					String hashemail = no.toString(16);
					while (hashemail.length() < 32) {
						hashemail = "0" + hashemail;
					}

					System.out.println("hash------------" + hashemail);

					String dirctorypath = UPLOADED_FOLDER + File.separator + hashemail;

					File f = new File(dirctorypath);

					if (mediaType.equalsIgnoreCase("image/jpeg") || mediaType.equalsIgnoreCase("image/png")
							|| mediaType.equalsIgnoreCase("image/jpg") || mediaType1.equalsIgnoreCase("image/jpg")
							|| mediaType1.equalsIgnoreCase("image/jpeg") || mediaType1.equalsIgnoreCase("image/png")
							|| mediaType1.equalsIgnoreCase("application/pdf")) {

						if (!(f.exists() && f.isDirectory())) {

							// File source = new File(UPLOADED_FOLDER + "user.png");
							File dest = new File(dirctorypath);
							boolean flag = dest.mkdir();
							if (flag) {
								System.out.println("Directory created successfully");
							} else {
								System.out.println("Sorry couldnt create specified directory");
							}

						}

						// Instant timestamp = Instant.now();
						Date date = new Date();

						// File userphoto = new File(dirctorypath + File.separator +
						// "pp_"+date.getTime()+"."+FilenameUtils.getExtension(profilepic.getOriginalFilename()));
						// File identity = new File(dirctorypath + File.separator +
						// "ip_"+date.getTime()+"."+FilenameUtils.getExtension(idproof.getOriginalFilename()));

						byte[] bytes = profilepic.getBytes();
						Path path = Paths.get(dirctorypath + File.separator + "pp_" + date.getTime() + "."
								+ FilenameUtils.getExtension(profilepic.getOriginalFilename()));
						Files.write(path, bytes);

						byte[] bytes1 = idproof.getBytes();
						Path path1 = Paths.get(dirctorypath + File.separator + "ip_" + date.getTime() + "."
								+ FilenameUtils.getExtension(idproof.getOriginalFilename()));
						Files.write(path1, bytes1);

						// profilepic.transferTo(userphoto);
						// idproof.transferTo(identity);
						return "success";
					} else {
						return "mimeerror";

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}

	}

	@GetMapping(value = "/getprofilepic/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImage(@PathVariable("userId") String userId) {

		String dirctorypath = UPLOADED_FOLDER + File.separator + userId;
		byte[] data = null;
		try {
			File x = new File(dirctorypath + File.separator + "user.png");
			File y = new File(dirctorypath);
			if (y.exists() && y.isDirectory()) {
				BufferedImage bImage = ImageIO.read(x);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bImage, "png", bos);
				data = bos.toByteArray();
				return data;
			} else {
				System.out.println("user image does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;

	}

	@GetMapping(value = "/getprofilepicfordis/{email}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImageforDiscussion(@PathVariable("email") String email) {

		String userId = null;
		List<LearnerMaster> lm = learnerDAO.findByEmail(email);
		if (lm.size() != 0) {
			userId = lm.get(0).getLearnerUsername();
			String dirctorypath = UPLOADED_FOLDER + File.separator + userId;
			byte[] data = null;
			try {
				File x = new File(dirctorypath + File.separator + "user.png");
				File y = new File(dirctorypath);
				if (y.exists() && y.isDirectory()) {
					BufferedImage bImage = ImageIO.read(x);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ImageIO.write(bImage, "png", bos);
					data = bos.toByteArray();
					return data;
				} else {
					System.out.println("user image does not exist");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		}

		else {
			return null;
		}
	}

	@GetMapping(value = "/getprofilepicforadminverification/{email}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getProfileFileforAdminverification(@PathVariable("email") String email) {
		byte[] data = null;
		if (email != null) {
		System.out.println("----hash email------" + email);
		FileService fileService = new FileService();
		List<File> allFiles = fileService.getFilesInDirectory(UPLOADED_FOLDER + File.separator + email);
		List<File> ppfilteredFiles = fileService.filterFilesBySubstring(allFiles, "pp_");
		// List<File> idfilteredFiles = fileService.filterFilesBySubstring(allFiles,
		// "ip_");
		String dirctorypath = UPLOADED_FOLDER + File.separator + email;
		File profilepic = new File(dirctorypath + File.separator + ppfilteredFiles.get(0));

		
		try {
			File x = new File(dirctorypath + File.separator + profilepic.getName());
			File y = new File(dirctorypath);
			if (y.exists() && y.isDirectory()) {
				BufferedImage bImage = ImageIO.read(x);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bImage, "png", bos);
				data = bos.toByteArray();
				return data;
			} else {
				System.out.println("user profile image does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return data;

	}

	@GetMapping(value = "/getidforadminverification/{email}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getIdFileforAdminverification(@PathVariable("email") String email) {
		byte[] data = null;
		if (email != null) {
			System.out.println("----hash email------" + email);
			FileService fileService = new FileService();
			List<File> allFiles = fileService.getFilesInDirectory(UPLOADED_FOLDER + File.separator + email);
			List<File> ppfilteredFiles = fileService.filterFilesBySubstring(allFiles, "ip_");
			// List<File> idfilteredFiles = fileService.filterFilesBySubstring(allFiles,
			// "ip_");
			String dirctorypath = UPLOADED_FOLDER + File.separator + email;
			File profilepic = new File(dirctorypath + File.separator + ppfilteredFiles.get(0));
			try {
				File x = new File(dirctorypath + File.separator + profilepic.getName());
				File y = new File(dirctorypath);
				if (y.exists() && y.isDirectory()) {
					BufferedImage bImage = ImageIO.read(x);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ImageIO.write(bImage, "png", bos);
					data = bos.toByteArray();
					return data;
				} else {
					System.out.println("id card does not exist");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return data;

	}

}