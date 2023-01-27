package com.cdac.dto;

import lombok.Data;

@Data
public class BulkUploadPojo {
	

	String firstName;
	String lastName;
	String emailId;
	String mobileNo;
	String status; // success or failure
	String remark; // why it lead to failure
	

}
