package com.cdac.web.controller;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.cdac.dao.StateDAO;
import com.cdac.dao.UserDocumentDAO;
import com.cdac.dao.UserReqInstructorDAO;

import com.cdac.dao.LearnerDAO;
import com.cdac.dao.LearnerStateCount;
import com.cdac.keycloak.service.KeycloakAdminClientService;
import com.cdac.model.CadreMaster;
import com.cdac.model.CountryMaster;
import com.cdac.model.DesignationMaster;
import com.cdac.model.DistrictMaster;
import com.cdac.model.StateMaster;
import com.cdac.model.UserDocument;
import com.cdac.model.UserReqInstructor;
import com.cdac.util.ActivationService;
import com.cdac.util.EmailSender;
import com.cdac.util.FileService;
import com.cdac.util.RandomPasswordGenerator;
import com.google.common.io.Files;
import com.cdac.model.LearnerMaster;
import com.cdac.model.QualificationMaster;
import com.cdac.dto.StatewiseTraineeCount;
import com.cdac.dto.AssignRole;
import com.cdac.dto.LearnerEditProfile;
import com.cdac.dto.LearnerMasterLitePojo;
import com.cdac.dto.LearnerMasterPojo;
import com.cdac.dto.RegisterLearner;

//@CrossOrigin("*")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/um_api")
@Configuration
@PropertySource("classpath:email.properties")
@PropertySource("classpath:application.properties")
public class LearnerController {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";
	public static final String ROLEASSIGNFAILED = "RoleAssignFailed";
	public static final String ROLEASSIGNSUCCESS = "RoleAssignSuccess";
	public static final String ROLEREMOVEDSUCCESS = "RoleRemovedSuccess";
	public static final String ROLEREMOVEDFAILED = "RoleRemovedFailed";

	@Autowired
	private EmailSender eMail;

	@Autowired
	private RandomPasswordGenerator rpg;

	@Autowired
	private ImageController imageController;

	@Autowired
	private KeycloakController keycloakController;

	@Value("${mail.subject}")
	private String subject;

	@Value("${mail.subject1}")
	private String subject1;

	@Value("${mail.subject2}")
	private String subject2;

	@Value("${mail.subject3}")
	private String subject3;

	@Value("${mail.subject4}")
	private String subject4;

	@Value("${mail.subject5}")
	private String subject5;

	@Value("${mail.subject6}")
	private String subject6;

	@Value("${mail.subject7}")
	private String subject7;

	@Value("${mail.subject8}")
	private String subject8;

	@Value("${mail.body.registartion1}")
	private String bodytext;

	@Value("${mail.body.registartion2}")
	private String bodytext1;

	@Value("${mail.body.registartion3}")
	private String bodytext2;

	@Value("${mail.body.registartion4}")
	private String bodytext3;

	@Value("${mail.body.registartion5}")
	private String bodytext8;

	@Value("${mail.body.registartion}")
	private String bodytext4;

	@Value("${mail.body.courseenrollment}")
	private String bodytext5;

	@Value("${mail.body.courseenrollmentapproval}")
	private String bodytext6;

	@Value("${mail.body.courseenrollmentrejection}")
	private String bodytext7;

	@Autowired
	private LearnerDAO learnerDAO;

	@Autowired
	private UserDocumentDAO userDocumentDAO;

	@Autowired
	private KeycloakAdminClientService keycloakAdminClientService;

	@Autowired
	private StateDAO stateDAO;

	@Autowired
	private UserReqInstructorDAO userInstDAO;

	@Value("${imagefolder.path}")
	String directorypath;

	// @Value("${server.servlet.context-path}")
	// String contextpath;

	/*
	 * public LearnerMaster savelearner(RegisterLearner registerLearner) {
	 * LearnerMaster lm = new LearnerMaster(); LearnerMaster lm1 = null;
	 * 
	 * try {
	 * 
	 * System.out.println("-----------" + registerLearner.getLearnerUsername() +
	 * registerLearner.getEmail() + registerLearner.getFirstName() +
	 * registerLearner.getLastName() + registerLearner.getMobile());
	 * lm.setLearnerUsername(registerLearner.getLearnerUsername());
	 * lm.setEmail(registerLearner.getEmail());
	 * lm.setFirstName(registerLearner.getFirstName());
	 * lm.setLastName(registerLearner.getLastName());
	 * 
	 * lm.setMobile(registerLearner.getMobile()); lm.setIsactive("true");
	 * lm.setStatus("registered"); lm.setUpdateBy(registerLearner.getUpdatedBy());
	 * lm.setUpdatedOn(new Date());
	 * lm.setDesignation(registerLearner.getDesignation());
	 * lm.setBiodata(registerLearner.getBiodata());
	 * lm.setFacebookId(registerLearner.getFacebookId());
	 * lm.setTwitterId(registerLearner.getTwitterId());
	 * lm.setYoutubeId(registerLearner.getYoutubeId());
	 * lm.setLinkedinId(registerLearner.getLinkedinId());
	 * lm.setSkypeId(registerLearner.getSkypeId());
	 * 
	 * 
	 * karthik- 26/11/20202
	 * 
	 * As country,state,district are not mandatory in adding trainee , but as they
	 * are reference from master table intializing there values to INDIA, DELHI, NEW
	 * DELHI
	 * 
	 * 
	 * CountryMaster cm = new CountryMaster(); cm.setCountryId(0);
	 * lm.setCountryMaster(cm);
	 * 
	 * StateMaster sm = new StateMaster(); sm.setStateId(0); lm.setStateMaster(sm);
	 * 
	 * DistrictMaster dm = new DistrictMaster(); dm.setDistrictId(0);
	 * lm.setDistrictMaster(dm);
	 * 
	 * 
	 * Code for creating directory with user id and copying dummy image at the time
	 * of registration
	 * 
	 * 
	 * File source = new File(directorypath + "user.png"); File dest = new
	 * File(directorypath + File.separator + lm.getLearnerUsername()); boolean flag
	 * = dest.mkdir(); if (flag) {
	 * System.out.println("Directory created successfully"); } else {
	 * System.out.println("Sorry couldnt create specified directory"); } File dest1
	 * = new File(dest + File.separator + "user.png"); Files.copy(source, dest1);
	 * 
	 * lm1 = learnerDAO.saveAndFlush(lm);
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * } return lm1; }
	 */
	
	@PostMapping("/checkemailstatus")
	public String checkEmailstatus(@RequestParam String email) {
		// System.out.println("trainee username"+learnerMasterdto.getTraineeUsername());
		//RegisterLearner registerLearner = new RegisterLearner();
		try {
			List<LearnerMaster> lm1 = learnerDAO.findByEmail(email);
			if (lm1.size() != 0) {
				return "available";

			} else {

				return "exists";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}

	}
	
	@PostMapping("/registerlearner")
	public String savelearner(@ModelAttribute RegisterLearner registerLearner,
			@RequestParam("file1") MultipartFile profilephoto, @RequestParam("file2") MultipartFile idcard) {
		LearnerMaster lm = new LearnerMaster();
		UserDocument ud = new UserDocument();
		LearnerMaster lm1 = null;
		String acttoken;
		String status = "error";
		try {

			System.out.println("-----------" + registerLearner.getLearnerUsername() + registerLearner.getEmail()
					+ registerLearner.getFirstName() + registerLearner.getLastName() + registerLearner.getMobile());
			lm.setLearnerUsername(registerLearner.getEmail());
			lm.setEmail(registerLearner.getEmail());
			lm.setFirstName(registerLearner.getFirstName());
			lm.setLastName(registerLearner.getLastName());
			lm.setMobile(registerLearner.getMobile());
			lm.setIsactive("true");
			lm.setStatus("registered");
			lm.setUpdateBy(registerLearner.getUpdatedBy());
			lm.setUpdatedOn(new Date());

			lm.setAuthenticationId(0);
			lm.setBeltNumber(registerLearner.getBeltNumber());
			lm.setGpfNumber(registerLearner.getGpfNumber());
			CadreMaster cdm = new CadreMaster();
			cdm.setCadreId(registerLearner.getCadreId());
			lm.setCadreMaster(cdm);

			DesignationMaster dgm = new DesignationMaster();
			dgm.setDesgId(registerLearner.getDesgId());
			lm.setDesignationMaster(dgm);

			QualificationMaster qm = new QualificationMaster();
			qm.setQualId(registerLearner.getQualId());
			lm.setQualificationMaster(qm);

			lm.setPlaceOfPosting(registerLearner.getPlaceOfPosting());

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date dateofbirth = formatter.parse(registerLearner.getDob());
			lm.setDob(dateofbirth);

			lm.setAddress(registerLearner.getAddress());

			/*
			 * karthik- 26/11/20202
			 * 
			 * As country,state,district are not mandatory in adding trainee , but as they
			 * are reference from master table intializing there values to INDIA, DELHI, NEW
			 * DELHI
			 */

			CountryMaster cm = new CountryMaster();
			cm.setCountryId(0);
			lm.setCountryMaster(cm);

			StateMaster sm = new StateMaster();
			sm.setStateId(0);
			lm.setStateMaster(sm);

			DistrictMaster dm = new DistrictMaster();
			dm.setDistrictId(0);
			lm.setDistrictMaster(dm);

			System.out.println("before calling activationservice");

			/* Code for cretaing activation token and **/
			ActivationService activationservice = new ActivationService();
			acttoken = activationservice.generateActivationToken(registerLearner.getEmail());
			lm.setActivationToken(acttoken);
			System.out.println("after calling activationservice");

			lm1 = learnerDAO.save(lm);

			/*
			 * Code for creating directory with user email MD5 hash at the time of
			 * registration
			 */

			System.out.println("before calling image controller");
			String filesstatus = imageController.FileUpload(profilephoto, idcard, registerLearner.getEmail());
			System.out.println("after calling image controller");

			if (filesstatus == "success") {
				ud.setPhotograph(profilephoto.getOriginalFilename());
				ud.setIdcard(idcard.getOriginalFilename());
			}

			/* for debugging purpose only */
			if (filesstatus == "failed") {
				System.out.println("errror uplaoding files");
			}
			if (filesstatus == "mimeerror") {
				System.out.println("invalid file type");
			}
			if (filesstatus == "invalidfiles") {
				System.out.println("not a valid file");
			}
			if (filesstatus == "invaliduserid") {
				System.out.println("invalid email");
			}

			// MessageDigest md = MessageDigest.getInstance("MD5");
			// byte[] hashemail = md.digest(registerLearner.getEmail().getBytes());

			// File source = new File(directorypath + "user.png");
			// File dest = new File(directorypath + File.separator + hashemail);
			// boolean flag = dest.mkdir();
			// if (flag) {
			// System.out.println("Directory created successfully");
			// } else {
			// System.out.println("Sorry couldnt create specified directory");
			// }

			// if (profilephoto.getSize() != 0) {
			// File userphoto = new File(dest + File.separator +
			// profilephoto.getOriginalFilename());
			// profilephoto.transferTo(userphoto);

			// }
			// if (idcard.getSize() != 0) {

			// File identity = new File(dest + File.separator +
			// idcard.getOriginalFilename());
			// idcard.transferTo(identity);

			// }
			ud.setEmailid(registerLearner.getEmail());
			UserDocument ud1 = userDocumentDAO.save(ud);

			String userdocstatus = "error";

			if (ud1.getEmailid().equals(ud.getEmailid())) {
				System.out.println("activation token----" + acttoken);
				String formattedtext = MessageFormat.format(bodytext3, lm1.getFirstName() + " " + lm1.getLastName(),
						acttoken);
				userdocstatus = "success";
				eMail.sendEmail(registerLearner.getEmail(), subject4, formattedtext);
			}

			status = "success";

		} catch (Exception e) {
			e.printStackTrace();
			

		}
		return status;
	}

	@PutMapping("/learneremailveristatus")
	public String updatemailverificationstatus(@RequestParam String token) {
		// System.out.println("trainee username"+learnerMasterdto.getTraineeUsername());

		try {
			List<LearnerMaster> lm1 = learnerDAO.findByActivationToken(token);
			if (lm1.size() != 0) {

				LearnerMaster lm = lm1.get(0);
                lm.setStatus("emailverified");
				lm.setAuthenticationId(1);
				lm.setActivationToken("NA");

				learnerDAO.save(lm);

				String formattedtext = MessageFormat.format(bodytext8,
						lm1.get(0).getFirstName() + " " + lm1.get(0).getLastName());
				eMail.sendEmail(lm1.get(0).getEmail(), subject5, formattedtext);
				// keycloakAdminClientService.updateLearnerName(lm);

				return SUCCESS;

			} else {

				return FAILED;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}

	}

	@GetMapping("/learnerlistforadminapproval")
	public List<LearnerMasterLitePojo> getlearnerlistforAdminApproval() {
		List<LearnerMasterLitePojo> lmList = new ArrayList<>();
		try {
			List<LearnerMaster> learnerObj = learnerDAO.findByAuthenticationId(1);

			System.out.println("----learnerObj size------" + learnerObj.size());
			LearnerMasterLitePojo lmp = null;

			for (LearnerMaster l : learnerObj) {
				lmp = new LearnerMasterLitePojo();

				lmp.setLearnerUsername(l.getLearnerUsername());
				lmp.setFirstName(l.getFirstName());
				lmp.setDob(l.getDob());
				lmp.setEduQualification(l.getQualificationMaster().getQualification());
				lmp.setEmail(l.getEmail());
				lmp.setGender(l.getGender());
				lmp.setLastName(l.getLastName());
				lmp.setMobile(l.getMobile().longValue());
				lmp.setBeltno(l.getBeltNumber());
				lmp.setGpfno(l.getGpfNumber());
				lmp.setDesignation(l.getDesignationMaster().getDesignation());
				lmp.setCadre(l.getCadreMaster().getCadre());
				lmp.setPlaceofposting(l.getPlaceOfPosting());
                
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] hashtext = md.digest(l.getEmail().getBytes());
				BigInteger no = new BigInteger(1, hashtext);
				String hashemail = no.toString(16);
				while (hashemail.length() < 32) {
					hashemail = "0" + hashemail;
				}

				/*
				 * System.out.println("----hash email------"+hashemail); FileService fileService
				 * = new FileService(); List<File> allFiles =
				 * fileService.getFilesInDirectory(directorypath + File.separator + hashemail);
				 * List<File> ppfilteredFiles = fileService.filterFilesBySubstring(allFiles,
				 * "pp_"); List<File> idfilteredFiles =
				 * fileService.filterFilesBySubstring(allFiles, "pp_");
				 * 
				 * File profilepic = new File(directorypath + File.separator + hashemail +
				 * ppfilteredFiles.get(0)); File idcard = new File(directorypath +
				 * File.separator + hashemail + idfilteredFiles.get(0));
				 */

				// idcard.getPath();

				lmp.setProfilepicpath(hashemail);
				lmp.setIdproofpicpath(hashemail);

				lmList.add(lmp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return lmList;

	}

	@PostMapping("/approvelearner")
	public String approveLearner(@RequestParam String email) {
		// System.out.println("trainee username"+learnerMasterdto.getTraineeUsername());
		RegisterLearner registerLearner = new RegisterLearner();
		try {
			List<LearnerMaster> lm1 = learnerDAO.findByEmail(email);
			if (lm1.size() != 0) {

				LearnerMaster lm = lm1.get(0);
				registerLearner.setEmail(lm.getEmail());
				registerLearner.setFirstName(lm.getFirstName());
				registerLearner.setLastName(lm.getLastName());
				registerLearner.setMobile(lm.getMobile());

				String keycloakid = keycloakController.createLearner(registerLearner);
            
				/*
				 * If User is approved by admin updating the learner userid in learner master
				 * with keycloak id and updating authentication status to 2 and copying profile
				 * pic from user hash folder to keycloak id folder for displaying in profile
				 * section
				 * 
				 */

				if (keycloakid != FAILED) {
					learnerDAO.delete(lm);
					System.out.println("----email------" +email+"-------keycloakid---------"+keycloakid);
					//learnerDAO.updateUsername(email, keycloakid);
					lm.setLearnerUsername(keycloakid);
					lm.setAuthenticationId(2); // code 2 for approved users
					lm.setStatus("approved");
					learnerDAO.save(lm);
					String pass = rpg.generateRandomPassword(8);
					//String formattedtext = MessageFormat.format(bodytext4,
						//	lm1.get(0).getFirstName() + " " + lm1.get(0).getLastName(), pass);
				//	eMail.sendEmail(lm1.get(0).getEmail(), subject, formattedtext);

					String dirctorypath1 = directorypath + File.separator + keycloakid;
					File f = new File(dirctorypath1);

					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] hashtext = md.digest(email.getBytes());
					BigInteger no = new BigInteger(1, hashtext);
					String hashemail = no.toString(16);
					while (hashemail.length() < 32) {
						hashemail = "0" + hashemail;
					}

					// MessageDigest md = MessageDigest.getInstance("MD5");
					// byte[] hashemail = md.digest(email.getBytes());

					if (!(f.exists() && f.isDirectory())) {

						// File directory = new File(directorypath + File.separator+hashemail);

						FileService fileService = new FileService();
						List<File> allFiles = fileService
								.getFilesInDirectory(directorypath + File.separator + hashemail);
						List<File> filteredFiles = fileService.filterFilesBySubstring(allFiles, "pp_");

						File source = new File(filteredFiles.get(0).getPath());
						System.out.println("Source---------"+source.getPath());
						File dest = new File(dirctorypath1);
						boolean flag = dest.mkdir();
						if (flag) {
							System.out.println("Directory created successfully");
						} else {
							System.out.println("Sorry couldnt create specified directory");
						}

						File dest1 = new File(dirctorypath1 + File.separator + "user.png");
						System.out.println("desti---------"+dest1.getPath());
						Files.copy(source, dest1);

					}
				}

				return SUCCESS;

			} else {

				return FAILED;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}

	}

	@PostMapping("/rejectlearner")
	public String rejectLearner(@RequestParam String email) {
		// System.out.println("trainee username"+learnerMasterdto.getTraineeUsername());
		RegisterLearner registerLearner = new RegisterLearner();
		try {
			List<LearnerMaster> lm1 = learnerDAO.findByEmail(email);
			if (lm1.size() != 0) {

				LearnerMaster lm = lm1.get(0);

				lm.setAuthenticationId(3); // code 3 for rejected users
				lm.setStatus("rejected");
				learnerDAO.save(lm);

				return "success";

			} else {

				return FAILED;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}

	}

	@GetMapping("/learner")
	public List<LearnerMasterPojo> get() {
		List<LearnerMaster> learnerObj = learnerDAO.findByAuthenticationId(2);
		LearnerMasterPojo lmp = null;
		List<LearnerMasterPojo> lmList = new ArrayList<>();
		for (LearnerMaster l : learnerObj) {
			lmp = new LearnerMasterPojo();

			lmp.setLearnerUsername(l.getLearnerUsername());
			lmp.setFirstName(l.getFirstName());
			lmp.setAddress(l.getAddress().toString());
			lmp.setCity(l.getCity());
			lmp.setDob(l.getDob());
			lmp.setEduQualification(l.getQualificationMaster().getQualification());
			lmp.setEmail(l.getEmail());
			lmp.setInstituteName(l.getInstituteName());
			lmp.setGender(l.getGender());
			lmp.setLastName(l.getLastName());
			lmp.setMobile(l.getMobile().longValue());
			lmp.setOfficialAddress(l.getOfficialAddress());
			lmp.setPincode(l.getPincode());
			lmp.setCountryId(l.getCountryMaster().getCountryId());
			lmp.setCountryName(l.getCountryMaster().getCountryName());
			lmp.setStateId(l.getStateMaster().getStateId());
			lmp.setStateName(l.getStateMaster().getStateName());
			lmp.setDistrictId(l.getDistrictMaster().getDistrictId());
			lmp.setDistrictName(l.getDistrictMaster().getDistrictName());
			lmp.setIsactive(l.getIsactive());
			lmp.setBeltno(l.getBeltNumber());
			lmp.setGpfno(l.getGpfNumber());
			lmp.setDesignation(l.getDesignationMaster().getDesignation());
			lmp.setCadre(l.getCadreMaster().getCadre());
			lmp.setPlaceofposting(l.getPlaceOfPosting());

			lmList.add(lmp);
		}
		return lmList;
	}

	@GetMapping("/learner/search/{searchtype}/{searchkey}")
	public List<LearnerMaster> get(@PathVariable String searchtype, @PathVariable String searchkey)
			throws ParseException {
		if (searchtype.replaceAll("\\s+", "").equals("EmailId")) {

			return learnerDAO.findByEmail(searchkey);

		} else if (searchtype.replaceAll("\\s+", "").equals("DOB")) {

			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
			Date searchkeydate = form.parse(searchkey);

			return learnerDAO.findByDob(searchkeydate);

		} else if (searchtype.replaceAll("\\s+", "").equals("Mobile")) {

			return learnerDAO.findByMobile(Long.parseLong(searchkey));

		}
		return learnerDAO.findAll();
	}

	@GetMapping("/learner/getallmeghinst")
	public List<LearnerMaster> getallMeghInstructorDetails() {

		return learnerDAO.findByStatus("inst");

	}

	@GetMapping("/learner/getmeghinst")
	public List<LearnerMaster> getMeghInstructorDetails(@RequestParam String[] searchkey) {
		List<LearnerMaster> learnerlist = new ArrayList<LearnerMaster>();

		try {
			for (String search : searchkey) {

				List<LearnerMaster> lm = learnerDAO.findByLearnerUsername(search);
				if (lm.size() > 0) {
					learnerlist.add(lm.get(0));
				}
			}

		} catch (Exception e) {
			System.out.println("The email you have entered is invalid");
		}
		return learnerlist;
	}

	@GetMapping("/learner/{id}")
	public LearnerMaster get(@PathVariable String id) {
		Optional<LearnerMaster> traineeObj = learnerDAO.findById(id);
		System.out.println("learner id------------------->" + id);
		if (traineeObj.isPresent()) {
			System.out.println("country name-----------" + traineeObj.get().getCountryMaster().getCountryId());
			System.out.println("state name-----------" + traineeObj.get().getStateMaster().getStateId());
			System.out.println("district name-----------" + traineeObj.get().getDistrictMaster().getDistrictId());
			return traineeObj.get();
		} else {
			throw new RuntimeException("Learner is not found for the Id:" + id);
		}
	}

	 @GetMapping("/getlearner/{id}")
	public LearnerMasterLitePojo getUserDetails(@PathVariable String id) {
		 
		// System.out.println("learner2 id------------------->" + id);
		Optional<LearnerMaster> traineeObj = learnerDAO.findById(id);
		//System.out.println("learner id------------------->" + id);
		System.out.println("cadre no id------------------->" + traineeObj.get().getCadreMaster().getCadre());
		System.out.println("Designation no id------------------->" + traineeObj.get().getDesignationMaster().getDesignation());
		System.out.println("Qualification------------------->" + traineeObj.get().getQualificationMaster().getQualification());
		LearnerMasterLitePojo lmlp = new LearnerMasterLitePojo();
		lmlp.setBeltno(traineeObj.get().getBeltNumber());
		lmlp.setCadre(traineeObj.get().getCadreMaster().getCadre());
		lmlp.setDesignation(traineeObj.get().getDesignationMaster().getDesignation());
		lmlp.setGpfno(traineeObj.get().getGpfNumber());
		lmlp.setPlaceofposting(traineeObj.get().getPlaceOfPosting());
		lmlp.setEduQualification(traineeObj.get().getQualificationMaster().getQualification());
		lmlp.setDob(traineeObj.get().getDob());
		lmlp.setEmail(traineeObj.get().getEmail());
		lmlp.setFirstName(traineeObj.get().getFirstName());
		lmlp.setLastName(traineeObj.get().getLastName());
		lmlp.setGender(traineeObj.get().getGender());
		lmlp.setProfilepicpath(traineeObj.get().getProfilePicPath());
		lmlp.setLearnerUsername(traineeObj.get().getLearnerUsername());
		System.out.println("setProfilepicpath------------------->" + id);
		lmlp.setMobile(traineeObj.get().getMobile().longValue());
		//imageController.getImage(id);
		
		return lmlp;
	}
	
	
	
	
	
	
	
	/*
	 * This method is used to provide user details in other microservice
	 */
	@GetMapping("/learner/byId")
	public LearnerMasterPojo getLearner(@RequestParam String userid) {
		Optional<LearnerMaster> learnerObj = learnerDAO.findById(userid);
		if (learnerObj.isPresent()) {
			// System.out.println("country
			// name-----------"+traineeObj.get().getCountryMaster().getCountryName());
			LearnerMasterPojo lmp = new LearnerMasterPojo();
			lmp.setLearnerUsername(learnerObj.get().getLearnerUsername());
			lmp.setFirstName(learnerObj.get().getFirstName());
			lmp.setAddress(learnerObj.get().getAddress().toString());
			lmp.setCity(learnerObj.get().getCity());
			lmp.setDob(learnerObj.get().getDob());
			lmp.setEduQualification(learnerObj.get().getQualificationMaster().getQualification());
			lmp.setEmail(learnerObj.get().getEmail());
			lmp.setInstituteName(learnerObj.get().getInstituteName());
			lmp.setGender(learnerObj.get().getGender());
			lmp.setLastName(learnerObj.get().getLastName());
			lmp.setMobile(learnerObj.get().getMobile().longValue());
			lmp.setOfficialAddress(learnerObj.get().getOfficialAddress());
			lmp.setPincode(learnerObj.get().getPincode());
			lmp.setCountryId(learnerObj.get().getCountryMaster().getCountryId());
			lmp.setCountryName(learnerObj.get().getCountryMaster().getCountryName());
			lmp.setStateId(learnerObj.get().getStateMaster().getStateId());
			lmp.setStateName(learnerObj.get().getStateMaster().getStateName());
			lmp.setDistrictId(learnerObj.get().getDistrictMaster().getDistrictId());
			lmp.setDistrictName(learnerObj.get().getDistrictMaster().getDistrictName());
			lmp.setBeltno(learnerObj.get().getBeltNumber());
			lmp.setGpfno(learnerObj.get().getGpfNumber());
			lmp.setDesignation(learnerObj.get().getDesignationMaster().getDesignation());
			lmp.setCadre(learnerObj.get().getCadreMaster().getCadre());
			lmp.setPlaceofposting(learnerObj.get().getPlaceOfPosting());

			return lmp;
		} else {
			throw new RuntimeException("Learner is not found for the Id:" + userid);
		}
	}

	/*
	 * @GetMapping("/trainee/{dob}") public TraineeMaster get(@PathVariable Date
	 * dob) { Optional<TraineeMaster> traineeObj = traineeDAO.find(dob); if
	 * (traineeObj.isPresent()) { return traineeObj.get(); } else { throw new
	 * RuntimeException("Trainee is not found for the Date of Birth:" + dob); } }
	 */

	@PutMapping("/updatelearner")
	public String update(@RequestBody LearnerEditProfile lerEditProfile) {
		// System.out.println("trainee username"+learnerMasterdto.getTraineeUsername());

		try {
			List<LearnerMaster> lm1 = learnerDAO.findByLearnerUsername(lerEditProfile.getLearnerUsername());
			if (lm1.size() != 0) {
				System.out.println("inside if" + lerEditProfile.getDob());
				LearnerMaster lm = lm1.get(0);
				int countryId = lerEditProfile.getCountryId();

				SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
				int stateId = lerEditProfile.getStateId();
				int districtId = lerEditProfile.getDistrictId();
				System.out.println("first name" + lerEditProfile.getFirstName());
				System.out.println(" learner name " + lm.getFirstName());
				Date dob = dfDate.parse(lerEditProfile.getDob());
				System.out.println("dob" + dob);
				// lm.setEmail(lerEditProfile.getEmail());
				lm.setFirstName(lerEditProfile.getFirstName());
				lm.setLastName(lerEditProfile.getLastName());
				lm.setDob(dob);
				
				lm.setBeltNumber(lerEditProfile.getBeltNumber());
				lm.setGpfNumber(lerEditProfile.getGpfNumber());
				
				CadreMaster cdm = new CadreMaster();
				cdm.setCadreId(lerEditProfile.getCadreId());
				lm.setCadreMaster(cdm);

				DesignationMaster dgm = new DesignationMaster();
				dgm.setDesgId(lerEditProfile.getDesgId());
				lm.setDesignationMaster(dgm);

				QualificationMaster qm = new QualificationMaster();
				qm.setQualId(lerEditProfile.getQualId());
				lm.setQualificationMaster(qm);
				//System.out.println("QualificationMaster" + lerEditProfile.getQualId());

				lm.setPlaceOfPosting(lerEditProfile.getPlaceOfPosting());

				lm.setCity(lerEditProfile.getCity());
				lm.setAddress(lerEditProfile.getAddress());
				lm.setGender(lerEditProfile.getGender());

				long mobile = lerEditProfile.getMobile();
				BigInteger mobilelong = BigInteger.valueOf(mobile);
				lm.setMobile(mobilelong);

				lm.setFacebookId(lerEditProfile.getFacebookId());
				lm.setTwitterId(lerEditProfile.getTwitterId());
				lm.setLinkedinId(lerEditProfile.getLinkedinId());
				lm.setYoutubeId(lerEditProfile.getYoutubeId());
				lm.setSkypeId(lerEditProfile.getSkypeId());

				lm.setPincode(lerEditProfile.getPincode());
				lm.setInstituteName(lerEditProfile.getInstituteName());

				lm.setStatus("updated");
				lm.setUpdateBy(lerEditProfile.getUpdatedBy());
				lm.setUpdatedOn(new Date());

				CountryMaster cm = new CountryMaster();
				System.out.println("country id" + countryId + stateId + districtId + lerEditProfile.getPincode());
				cm.setCountryId(countryId);
				lm.setCountryMaster(cm);

				StateMaster sm = new StateMaster();
				sm.setStateId(stateId);
				lm.setStateMaster(sm);

				DistrictMaster dm = new DistrictMaster();
				dm.setDistrictId(districtId);
				lm.setDistrictMaster(dm);

				learnerDAO.save(lm);

				keycloakAdminClientService.updateLearnerName(lm);

				return SUCCESS;

			} else {

				return FAILED;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}

	}

	@DeleteMapping("/learner/{id}")
	public String delete(@PathVariable String id) {
		Optional<LearnerMaster> learner = learnerDAO.findById(id);
		if (learner.isPresent()) {
			learnerDAO.delete(learner.get());
			return SUCCESS;
		} else {
			// throw new RuntimeException("Trainee is not found for the id " + id);
			return FAILED;
		}
	}

	@GetMapping("/learner/getcountbystate")
	public List<StatewiseTraineeCount> getCountbyStates() {
		List<StatewiseTraineeCount> stc = new ArrayList<StatewiseTraineeCount>();
		List<LearnerStateCount> tsc = learnerDAO.getAllstatesLearnerCount();
		for (LearnerStateCount st : tsc) {
			StatewiseTraineeCount stco = new StatewiseTraineeCount();
			stco.setCount(st.getTotal());
			stco.setStateid(st.getState());
			String statename = stateDAO.findByStateId(st.getState()).get(0).getStateName();
			stco.setStatename(statename);
			stc.add(stco);
		}
		return stc;
	}

	@GetMapping("/learner/getInstructorList")
	public List<LearnerMasterPojo> getInstrutorList() {
		Set<UserRepresentation> data = keycloakAdminClientService.getInstructorsList();

		// populate set
		LearnerMasterPojo lmp = null;
		List<LearnerMasterPojo> lmList = new ArrayList<>();
		for (UserRepresentation s : data) {
			Optional<LearnerMaster> learnerObj = learnerDAO.findById(s.getId());
			if (learnerObj.isPresent()) {
				learnerObj.get().getStatus();
				lmp = new LearnerMasterPojo();
				if (s.isEnabled() == true) {
					lmp.setStatus("true");
				} else {
					lmp.setStatus("false");
				}
				lmp.setLearnerUsername(learnerObj.get().getLearnerUsername());
				lmp.setFirstName(learnerObj.get().getFirstName());
				lmp.setAddress(learnerObj.get().getAddress().toString());
				lmp.setCity(learnerObj.get().getCity());
				lmp.setDob(learnerObj.get().getDob());
				lmp.setEduQualification(learnerObj.get().getQualificationMaster().getQualification());
				;
				// lmp.setEduQualification(learnerObj.get().getEduQualification());
				lmp.setEmail(learnerObj.get().getEmail());
				lmp.setInstituteName(learnerObj.get().getInstituteName());
				lmp.setGender(learnerObj.get().getGender());
				lmp.setLastName(learnerObj.get().getLastName());
				lmp.setMobile(learnerObj.get().getMobile().longValue());
				lmp.setOfficialAddress(learnerObj.get().getOfficialAddress());
				lmp.setPincode(learnerObj.get().getPincode());
				lmp.setCountryId(learnerObj.get().getCountryMaster().getCountryId());
				lmp.setCountryName(learnerObj.get().getCountryMaster().getCountryName());
				lmp.setStateId(learnerObj.get().getStateMaster().getStateId());
				lmp.setStateName(learnerObj.get().getStateMaster().getStateName());
				lmp.setDistrictId(learnerObj.get().getDistrictMaster().getDistrictId());
				lmp.setDistrictName(learnerObj.get().getDistrictMaster().getDistrictName());
				lmList.add(lmp);

			}
		}
		return lmList;
	}

	@GetMapping("/UserRequestinstructorStatus/{userId}")
	public String UserRequestinstructorStatus(@PathVariable String userId) {

		String status = "NA";
		List<UserReqInstructor> us = null;
		us = userInstDAO.findByuserId(userId);
		if (us != null & us.size() > 0) {
			status = us.get(0).getStatus();

		}
		return status;
	}

	@PostMapping("/UserRequestinstructor/{userId}")
	public String UserRequestinstructor(@PathVariable String userId) {

		try {
			// Optional<LearnerMaster> learnerObj =
			// learnerDAO.findById(user.get(0).getUserId());
			Date date = new Date();
			UserReqInstructor us = new UserReqInstructor();
			us.setUserId(userId);
			us.setStatus("pending");
			us.setRemarks("requested for instructor role");
			Timestamp ertimestamp = new Timestamp(System.currentTimeMillis());
			us.setRegtime(ertimestamp);
			// us.setRegtime(new Timestamp(System.currentTimeMillis()));
			userInstDAO.save(us);
			String formattedtext = MessageFormat.format(bodytext2, "MeghSikshak" + " " + "admin");
			eMail.sendEmail("meghsikshak@cdac.in", subject3, formattedtext);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FAILED;
	}

	@GetMapping("/getUserRequestInstructorList")
	public List<LearnerMasterPojo> getUserRequestInst(@RequestParam String status) {

		// System.out.println("status--------"+status);
		/* status can be "approved", "rejected", "pending" */
		List<UserReqInstructor> userreqinstlist = userInstDAO.findByreqStatus(status);

//		System.out.println("userreqinstlist--------"+userreqinstlist.size());

		List<LearnerMasterPojo> lmList = new ArrayList<>();
		for (UserReqInstructor s : userreqinstlist) {

			Optional<LearnerMaster> learnerObj = learnerDAO.findById(s.getUserId());
//				System.out.println("useris--------"+learnerObj.get().getFirstName());
			LearnerMasterPojo lmp = new LearnerMasterPojo();
			lmp.setLearnerUsername(learnerObj.get().getLearnerUsername());
			lmp.setFirstName(learnerObj.get().getFirstName());
			lmp.setAddress(learnerObj.get().getAddress().toString());
			lmp.setCity(learnerObj.get().getCity());
			lmp.setDob(learnerObj.get().getDob());
			lmp.setEduQualification(learnerObj.get().getQualificationMaster().getQualification());
			lmp.setEmail(learnerObj.get().getEmail());
			lmp.setInstituteName(learnerObj.get().getInstituteName());
			lmp.setGender(learnerObj.get().getGender());
			lmp.setLastName(learnerObj.get().getLastName());
			lmp.setMobile(learnerObj.get().getMobile().longValue());
			lmp.setOfficialAddress(learnerObj.get().getOfficialAddress());
			lmp.setPincode(learnerObj.get().getPincode());
			lmp.setCountryId(learnerObj.get().getCountryMaster().getCountryId());
			lmp.setCountryName(learnerObj.get().getCountryMaster().getCountryName());
			lmp.setStateId(learnerObj.get().getStateMaster().getStateId());
			lmp.setStateName(learnerObj.get().getStateMaster().getStateName());
			lmp.setDistrictId(learnerObj.get().getDistrictMaster().getDistrictId());
			lmp.setDistrictName(learnerObj.get().getDistrictMaster().getDistrictName());
			lmList.add(lmp);

		}
		return lmList;
	}

	@PutMapping("/rejectInstRequest")
	public String rejectInstRequest(@RequestParam String id, @RequestParam String remarks) {
		try {
			List<UserReqInstructor> user = userInstDAO.findByuserId(id);
			UserReqInstructor us = user.get(0);
			Optional<LearnerMaster> learnerObj = learnerDAO.findById(user.get(0).getUserId());
			// System.out.println(learnerObj.get().getEmail());
			if (user.size() > 0) {

				us.setStatus("rejected");
				us.setRemarks(remarks);
				userInstDAO.save(us);
				// System.out.println(learnerObj.get().getEmail());
				String formattedtext = MessageFormat.format(bodytext1,
						learnerObj.get().getFirstName() + " " + learnerObj.get().getLastName());
				eMail.sendEmail(learnerObj.get().getEmail(), subject2, formattedtext);
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FAILED;
	}

	@PutMapping("/approveInstRequest/{id}")
	public String approveInstRequest(@PathVariable String id) {
		String status = "FAILED";
		try {
			System.out.println("---------------------" + id);

			List<UserReqInstructor> user = userInstDAO.findByuserId(id);
			UserReqInstructor us = user.get(0);
			Optional<LearnerMaster> learnerObj = learnerDAO.findById(user.get(0).getUserId());
			if (user.size() > 0) {

				// courseCatalogClient.saveTenantCourseMapDetails(0, id, id)
				AssignRole ar = new AssignRole();
				ar.setRolename("instructor");
				ar.setUsername(id);
				String roleassignstatus = keycloakAdminClientService.assignClientInstRole(ar);
				System.out.println(roleassignstatus);
				if (roleassignstatus == ROLEASSIGNSUCCESS) {
					us.setStatus("approved");
					us.setRemarks("User approved as instructor");
					userInstDAO.save(us);
					String formattedtext = MessageFormat.format(bodytext,
							learnerObj.get().getFirstName() + " " + learnerObj.get().getLastName());
					eMail.sendEmail(learnerObj.get().getEmail(), subject1, formattedtext);
					status = SUCCESS;
				} else {
					us.setStatus("pending");
					us.setRemarks("User approval failed");
					userInstDAO.save(us);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@PostMapping("/SendEmailNotifofCourseEnroll")
	public void sendEmailforCourseEnrollStatus(@RequestParam String userId, @RequestParam String cname,
			@RequestParam int status) { // method for sending course enrollment status to users from course catalog
										// service. this method will be called from feign client

		try {
			Optional<LearnerMaster> learnerObj = learnerDAO.findById(userId);
			// System.out.println("learner id------------------->" + id);
			if (status == 4) {
				String formattedtext = MessageFormat.format(bodytext5,
						learnerObj.get().getFirstName() + " " + learnerObj.get().getLastName(), cname);
				eMail.sendEmail(learnerObj.get().getEmail(), subject6, formattedtext);
			} else if (status == 1) {
				String formattedtext = MessageFormat.format(bodytext6,
						learnerObj.get().getFirstName() + " " + learnerObj.get().getLastName(), cname);
				eMail.sendEmail(learnerObj.get().getEmail(), subject7, formattedtext);
			} else if (status == 5) {
				String formattedtext = MessageFormat.format(bodytext7,
						learnerObj.get().getFirstName() + " " + learnerObj.get().getLastName(), cname);
				eMail.sendEmail(learnerObj.get().getEmail(), subject8, formattedtext);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

}
