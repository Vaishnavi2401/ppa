package com.cdac.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//import org.omg.CORBA.PUBLIC_MEMBER;

public class TimeValidation {

	/*
	 * public static void main(String[] args) { TimeValidation tm = new
	 * TimeValidation();
	 * 
	 * tm.checkTime("14:20", "15:20", "09:30", "10:50");
	 * 
	 * }
	 */

	public String checkTime(String inputstartTime, String inputendTime, String dbstartTime, String dbendTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US);
		LocalTime inputLocalstartTime = LocalTime.parse(inputstartTime, formatter);
		LocalTime inputLocalendTime = LocalTime.parse(inputendTime, formatter);
		LocalTime dbLocalStartTime = LocalTime.parse(dbstartTime, formatter);
		LocalTime dbLocalendTime = LocalTime.parse(dbendTime, formatter);

		boolean isInBetween = false;

		if ((inputLocalendTime.equals(dbLocalStartTime))) {

			if (!(dbLocalendTime.isAfter(inputLocalendTime))) {

				isInBetween = true;

			}

		} else if ((inputLocalstartTime.equals(dbLocalendTime))) {

			if (inputLocalstartTime.isAfter(dbLocalStartTime) && inputLocalstartTime.isBefore(dbLocalendTime)) {

				isInBetween = true;

			} else if (inputLocalstartTime.isBefore(dbLocalStartTime) && inputLocalendTime.isBefore(dbLocalendTime)) {

				isInBetween = true;
			}

			else if (inputLocalstartTime.isAfter(dbLocalStartTime) && inputLocalendTime.isBefore(dbLocalendTime)) {

				isInBetween = true;
			} else if (inputLocalstartTime.isBefore(dbLocalStartTime) && inputLocalendTime.isAfter(dbLocalendTime)) {

				isInBetween = true;
			} else if (inputLocalstartTime.equals(dbLocalStartTime) && inputLocalendTime.isAfter(dbLocalendTime)) {

				isInBetween = true;
			}
		} else {

			if (inputLocalstartTime.isAfter(dbLocalStartTime) && inputLocalstartTime.isBefore(dbLocalendTime)) {

				isInBetween = true;

			} else if (inputLocalstartTime.isBefore(dbLocalStartTime) && inputLocalendTime.isBefore(dbLocalendTime)) {

				isInBetween = true;
			}

			else if (inputLocalstartTime.isAfter(dbLocalStartTime) && inputLocalendTime.isBefore(dbLocalendTime)) {

				isInBetween = true;
			} else if (inputLocalstartTime.isBefore(dbLocalStartTime) && inputLocalendTime.isAfter(dbLocalendTime)) {

				isInBetween = true;
			} else if (inputLocalstartTime.equals(dbLocalStartTime) && inputLocalendTime.isAfter(dbLocalendTime)) {

				isInBetween = true;
			}
		}

		if (isInBetween) {
			System.out.println("Is in between!");
			return "Error";
		} else {
			return "success";
		}
	}

}
