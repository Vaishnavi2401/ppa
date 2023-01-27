package com.cdac.web.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.cdac.dto.BulkUploadPojo;
import com.cdac.dto.RegisterLearner;

@CrossOrigin("*")
@RestController
@RequestMapping("/um_bulk")
public class BulkUploadController {

	@Autowired
	KeycloakController keyCloakController;

	@PostMapping(value = "upload")
	public List<BulkUploadPojo> getTraineeDetailsfromExcel(@RequestParam("file") MultipartFile regExcelFile,
			 @NotNull @RequestParam String updatedBy) {

		System.out.println("----initial hit");

		XSSFWorkbook workbook;
		List<BulkUploadPojo> returnStatus = new ArrayList<>();

		try {

			List<BulkUploadPojo> bulkList = new ArrayList<>();

			String namePattern = "[a-zA-Z]+";
			String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
			String mobilePattern = "^\\d{10}$";

			if (regExcelFile.isEmpty()) {

				throw new RuntimeException("File Cannot be Empty");
			} else {

				Tika tika = new Tika();

				String mediaType = tika.detect(regExcelFile.getBytes());
				 System.out.println("----media type "+mediaType);

				if (!mediaType.equalsIgnoreCase("application/x-tika-ooxml")
						&& !mediaType.equalsIgnoreCase("application/zip")) {

					throw new RuntimeException("Upload Only Excel File");

				}

			}

			workbook = new XSSFWorkbook(regExcelFile.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);

			 System.out.println("last row is "+worksheet.getLastRowNum());

			for (int i = 1; i <= worksheet.getLastRowNum(); i++) {

				XSSFRow row = worksheet.getRow(i);
				String message = "";
				String firstName = "NA";
				String lastName = "NA";
				String emailId = "NA";
				String mobileNo = "NA";

				if (row.getCell(0) != null) {
					firstName = row.getCell(0).getStringCellValue();
				}

				if (row.getCell(1) != null) {
					lastName = row.getCell(1).getStringCellValue();
				}
				if (row.getCell(2) != null) {
					emailId = row.getCell(2).getStringCellValue();
				}
				if (row.getCell(3) != null) {
					mobileNo = String.format("%.0f", row.getCell(3).getNumericCellValue());
				}

				// validation

				if (!firstName.trim().equals("NA") && !lastName.trim().equals("NA") && !emailId.trim().equals("NA")
						&& !mobileNo.trim().equals("NA")) {

					BulkUploadPojo bulkUploadPojo = new BulkUploadPojo();

					bulkUploadPojo.setFirstName(HtmlUtils.htmlEscape(firstName));
					bulkUploadPojo.setLastName(HtmlUtils.htmlEscape(lastName));
					bulkUploadPojo.setEmailId(HtmlUtils.htmlEscape(emailId));
					bulkUploadPojo.setMobileNo(HtmlUtils.htmlEscape(mobileNo));
					bulkUploadPojo.setStatus("Success");

					if (firstName.trim().isEmpty()) {

						message = message + "*First Name cannot be empty";
						bulkUploadPojo.setStatus("Fail");

					} else if (!firstName.trim().matches(namePattern)) {

						message = message + "*First name should contain only alphabets.";
						bulkUploadPojo.setStatus("Fail");

					}

					if (lastName.trim().isEmpty()) {

						message = message + "*Last Name cannot be empty.";
						bulkUploadPojo.setStatus("Fail");

					} else if (!lastName.trim().matches(namePattern)) {

						message = message + "*last name should contain only alphabets.";
						bulkUploadPojo.setStatus("Fail");

					}

					if (emailId.trim().isEmpty()) {

						message = message + "*Email Id cannot be empty.";
						bulkUploadPojo.setStatus("Fail");

					} else if (!emailId.trim().matches(emailPattern)) {

						message = message + "*Please enter valid EmailId.";
						bulkUploadPojo.setStatus("Fail");

					}

					if (mobileNo.trim().isEmpty()) {

						message = message + "*Mobile No cannot be empty.";
						bulkUploadPojo.setStatus("Fail");

					} else if (!mobileNo.trim().matches(mobilePattern)) {

						message = message + "*Please enter valid 10 digit mobile no.";
						bulkUploadPojo.setStatus("Fail");

					}
					bulkUploadPojo.setRemark(message);
					bulkList.add(bulkUploadPojo);

				}
			}

			for (BulkUploadPojo bulkpojo : bulkList) {

				// not equal to fail
				if (bulkpojo.getStatus().equals("Success")) {

					RegisterLearner dto = new RegisterLearner();

					// LearnerMasterDTO dto = new LearnerMasterDTO();
					dto.setFirstName(bulkpojo.getFirstName());
					dto.setLastName(bulkpojo.getLastName());
					dto.setLearnerUsername(bulkpojo.getEmailId());
					dto.setEmail(bulkpojo.getEmailId());
					BigInteger bigIntmobile = BigInteger.valueOf(Long.parseLong(bulkpojo.getMobileNo()));
					dto.setMobile(bigIntmobile);
					// dto.setProgramId(programId);
					dto.setUpdatedBy(updatedBy);

					// List<RankMaster> rankMaster= rankController.get();
					// dto.setRankId(rankMaster.get(0).getRankId()); //temp setting 0th rankId from
					// list of rastmaster

					String status = keyCloakController.createLearner(dto);

					if (status.equalsIgnoreCase(KeycloakController.SUCCESS)) {

						bulkpojo.setStatus("Success");
						bulkpojo.setRemark("-");

					} else if (status.equalsIgnoreCase(KeycloakController.EXISTS)) {

						bulkpojo.setStatus("Fail");
						bulkpojo.setRemark("User Already Exists");

					} else if (status.equalsIgnoreCase(KeycloakController.FAILED)) {

						bulkpojo.setStatus("Fail");
						bulkpojo.setRemark("Failed due to technical issue.Contact Administrator");

					}

				}

				returnStatus.add(bulkpojo);

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return returnStatus;

	}

}
