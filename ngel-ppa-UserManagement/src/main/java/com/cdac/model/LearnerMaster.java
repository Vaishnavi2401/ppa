package com.cdac.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the learner_master database table.
 * 
 */
@Entity
@Table(name="learner_master")
@NamedQuery(name="LearnerMaster.findAll", query="SELECT l FROM LearnerMaster l")
public class LearnerMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LEARNER_USERNAME")
	private String learnerUsername;

	@Column(name="ACTIVATION_TOKEN")
	private String activationToken;

	private String address;

	@Column(name="AUTHENTICATION_ID")
	private int authenticationId;

	@Column(name="BELT_NUMBER")
	private String beltNumber;

	private String biodata;

	private String city;

	@Temporal(TemporalType.DATE)
	private Date dob;

	private String email;

	@Column(name="FACEBOOK_ID")
	private String facebookId;

	@Column(name="FIRST_NAME")
	private String firstName;

	private String gender;

	@Column(name="GPF_NUMBER")
	private String gpfNumber;

	@Column(name="INSTITUTE_NAME")
	private String instituteName;

	private String isactive;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="LINKEDIN_ID")
	private String linkedinId;

	private BigInteger mobile;

	@Column(name="OFFICIAL_ADDRESS")
	private String officialAddress;

	private int pincode;

	@Column(name="PLACE_OF_POSTING")
	private String placeOfPosting;

	@Column(name="PROFILE_PIC_PATH")
	private String profilePicPath;

	@Column(name="REGISTERED_DATE")
	private Timestamp registeredDate;

	@Column(name="SKYPE_ID")
	private String skypeId;

	private String status;

	@Column(name="TWITTER_ID")
	private String twitterId;

	@Column(name="UPDATE_BY")
	private String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_ON")
	private Date updatedOn;

	@Column(name="USER_ID")
	private int userId;

	@Column(name="YOUTUBE_ID")
	private String youtubeId;

	//bi-directional many-to-one association to CadreMaster
	@ManyToOne
	@JoinColumn(name="CADRE")
	private CadreMaster cadreMaster;

	//bi-directional many-to-one association to CountryMaster
	@ManyToOne
	@JoinColumn(name="COUNTRY_ID")
	private CountryMaster countryMaster;

	//bi-directional many-to-one association to DesignationMaster
	@ManyToOne
	@JoinColumn(name="DESIGNATION")
	private DesignationMaster designationMaster;

	//bi-directional many-to-one association to DistrictMaster
	@ManyToOne
	@JoinColumn(name="DISTRICT_ID")
	private DistrictMaster districtMaster;

	//bi-directional many-to-one association to QualificationMaster
	@ManyToOne
	@JoinColumn(name="EDU_QUALIFICATION")
	private QualificationMaster qualificationMaster;

	//bi-directional many-to-one association to StateMaster
	@ManyToOne
	@JoinColumn(name="STATE_ID")
	private StateMaster stateMaster;

	public LearnerMaster() {
	}

	public String getLearnerUsername() {
		return this.learnerUsername;
	}

	public void setLearnerUsername(String learnerUsername) {
		this.learnerUsername = learnerUsername;
	}

	public String getActivationToken() {
		return this.activationToken;
	}

	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAuthenticationId() {
		return this.authenticationId;
	}

	public void setAuthenticationId(int authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getBeltNumber() {
		return this.beltNumber;
	}

	public void setBeltNumber(String beltNumber) {
		this.beltNumber = beltNumber;
	}

	public String getBiodata() {
		return this.biodata;
	}

	public void setBiodata(String biodata) {
		this.biodata = biodata;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookId() {
		return this.facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGpfNumber() {
		return this.gpfNumber;
	}

	public void setGpfNumber(String gpfNumber) {
		this.gpfNumber = gpfNumber;
	}

	public String getInstituteName() {
		return this.instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLinkedinId() {
		return this.linkedinId;
	}

	public void setLinkedinId(String linkedinId) {
		this.linkedinId = linkedinId;
	}

	public BigInteger getMobile() {
		return this.mobile;
	}

	public void setMobile(BigInteger mobile) {
		this.mobile = mobile;
	}

	public String getOfficialAddress() {
		return this.officialAddress;
	}

	public void setOfficialAddress(String officialAddress) {
		this.officialAddress = officialAddress;
	}

	public int getPincode() {
		return this.pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getPlaceOfPosting() {
		return this.placeOfPosting;
	}

	public void setPlaceOfPosting(String placeOfPosting) {
		this.placeOfPosting = placeOfPosting;
	}

	public String getProfilePicPath() {
		return this.profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}

	public Timestamp getRegisteredDate() {
		return this.registeredDate;
	}

	public void setRegisteredDate(Timestamp registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getSkypeId() {
		return this.skypeId;
	}

	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTwitterId() {
		return this.twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getYoutubeId() {
		return this.youtubeId;
	}

	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}

	public CadreMaster getCadreMaster() {
		return this.cadreMaster;
	}

	public void setCadreMaster(CadreMaster cadreMaster) {
		this.cadreMaster = cadreMaster;
	}

	public CountryMaster getCountryMaster() {
		return this.countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

	public DesignationMaster getDesignationMaster() {
		return this.designationMaster;
	}

	public void setDesignationMaster(DesignationMaster designationMaster) {
		this.designationMaster = designationMaster;
	}

	public DistrictMaster getDistrictMaster() {
		return this.districtMaster;
	}

	public void setDistrictMaster(DistrictMaster districtMaster) {
		this.districtMaster = districtMaster;
	}

	public QualificationMaster getQualificationMaster() {
		return this.qualificationMaster;
	}

	public void setQualificationMaster(QualificationMaster qualificationMaster) {
		this.qualificationMaster = qualificationMaster;
	}

	public StateMaster getStateMaster() {
		return this.stateMaster;
	}

	public void setStateMaster(StateMaster stateMaster) {
		this.stateMaster = stateMaster;
	}

}