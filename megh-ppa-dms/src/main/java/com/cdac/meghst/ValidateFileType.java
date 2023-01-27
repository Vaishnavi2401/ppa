package com.cdac.meghst;

public class ValidateFileType {
	public static boolean validateImageMimeType(String mimeType) {
		if (mimeType.equals("image/jpeg") || mimeType.equals("image/png")) {
			return true;
		}
		return false;
	}

	public static boolean validateVideoMimeType(String mimeType) {
		if (mimeType.equals("video/ogg") || mimeType.equals("video/webm") || mimeType.equals("video/quicktime")
				|| mimeType.equals("video/mp4") || mimeType.equals("video/mpeg")) {
			return true;
		}
		return false;
	}

	public static boolean validateHtmlMimeType(String mimeType) {
		if (mimeType.equals("text/html")) {
			return true;
		}
		return false;
	}

	public static boolean validatePDFMimeType(String mimeType) {
		if (mimeType.equals("application/pdf")) {
			return true;
		}
		return false;
	}

	public static boolean validateZIPMimeType(String mimeType) {
		if (mimeType.equals("application/zip") || mimeType.equals("application/x-compressed-zip")
				|| mimeType.equals("application/x-7z-compressed") || mimeType.equals("application/octet-stream")) {
			return true;
		}
		return false;
	}

	public static boolean validateTextMimeType(String mimeType) {
		if (mimeType.equals("text/plain")) {
			return true;
		}
		return false;
	}

	public static boolean validateExcelMimeType(String mimeType) {
		if (mimeType.equals("application/vnd.ms-excel")
				|| mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		}
		return false;
	}

	public static boolean validateDocMimeType(String mimeType) {
		if (mimeType.equals("application/msword")
				|| mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
			return true;
		}
		return false;
	}
}
