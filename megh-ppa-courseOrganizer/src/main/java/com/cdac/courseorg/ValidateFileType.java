package com.cdac.courseorg;

public class ValidateFileType {
	public static boolean validateImageMimeType(String mimeType) {
		if (mimeType.equals("image/jpeg") || mimeType.equals("image/png")) {
			return true;
		}
		return false;
	}
	
	public static boolean validateVideoMimeType(String mimeType) {
		if (mimeType.equals("video/ogg") || mimeType.equals("video/webm") || mimeType.equals("video/mp4") ||  mimeType.equals("video/quicktime")
				|| mimeType.equals("video/mpeg")) {
			return true;
		}
		return false;
	}

}
