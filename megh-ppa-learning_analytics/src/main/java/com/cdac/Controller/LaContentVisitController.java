package com.cdac.Controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cdac.Entity.LaContentVisit;
import com.cdac.Services.LaContentVisitService;
import com.cdac.Utils.LaContentAccess;
import com.cdac.Utils.LaContentTimespent;

@CrossOrigin("*")
@RequestMapping(value = "learning_analytics")
@Validated
@RestController
public class LaContentVisitController {

	@Autowired
	private LaContentVisitService laContentVisitService;

	// RESTful API methods for Retrieval operations
	@GetMapping("/lacontentvisit")
	public List<LaContentVisit> list() {
		return laContentVisitService.listAll();
	}

	@GetMapping("/contentaccess/{userid}/{courseid}")
	public List<LaContentAccess> contentaccess(
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "sessionid  pattern not matching ") String userid,
			@PathVariable @Pattern(regexp = "^([1-9][0-9]{0,2}|1000)$", message = "courseid  pattern not matching ") String courseid) {
		List<LaContentVisit> lconvisit = laContentVisitService.getByUserIdAndCourseId(userid, courseid);
		List<LaContentAccess> la = new ArrayList<LaContentAccess>();
		LaContentAccess la1 = null;

		for (LaContentVisit l : lconvisit) {
			la1 = new LaContentAccess();
			la1.setResTitle(l.getResTitle());
			la1.setInTime(l.getInTime());
			la1.setOutTime(l.getOutTime());
			la.add(la1);
		}

		return la;

	}

	@GetMapping("/contentaccessbydaterange/{userid}/{courseid}")
	public List<LaContentAccess> contentaccessbydaterange(
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "userid  pattern not matching ") String userid,
			@PathVariable @Pattern(regexp = "^([1-9][0-9]{0,2}|1000)$", message = "courseid  pattern not matching ") String courseid,
			@RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate) throws ParseException {

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = sdf1.parse(fromdate + " 00:00:00");
		// java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
		Timestamp sqlStartDate = new Timestamp(date.getTime());
		java.util.Date date1 = sdf1.parse(todate + " 23:59:59");
		// java.sql.Date sqlEndDate = new java.sql.Date(date1.getTime());
		Timestamp sqlEndDate = new Timestamp(date1.getTime());

		List<LaContentVisit> lconvisit = laContentVisitService.getByUserIdAndCourseIdAndDaterange(userid, courseid,
				sqlStartDate, sqlEndDate);

		List<LaContentAccess> la = new ArrayList<LaContentAccess>();
		LaContentAccess la1 = null;

		for (LaContentVisit l : lconvisit) {
			la1 = new LaContentAccess();
			la1.setResTitle(l.getResTitle());
			la1.setInTime(l.getInTime());
			la1.setOutTime(l.getOutTime());
			la.add(la1);
		}

		return la;

	}

	@GetMapping("/timespent/{userid}/{courseid}")
	public String timespent(
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "sessionid  pattern not matching ") String userid,
			@PathVariable @Pattern(regexp = "^([1-9][0-9]{0,2}|1000)$", message = "courseid  pattern not matching ") String courseid) {
		long milliseconds = 0;
		List<LaContentVisit> lconvisit = laContentVisitService.getByUserIdAndCourseId(userid, courseid);
		if (lconvisit.size() != 0) {
			for (LaContentVisit l : lconvisit) {
				milliseconds += l.getOutTime().getTime() - l.getInTime().getTime();

			}
			int seconds = (int) milliseconds / 1000;

			int hours = seconds / 3600;
			int minutes = (seconds % 3600) / 60;
			seconds = (seconds % 3600) % 60;
			/*
			 * String shours,sminutes,sseconds; if (hours<10) shours="0"+hours; else
			 * shours=""+hours; if (minutes<10) sminutes="0"+minutes; else
			 * sminutes=""+minutes; if (seconds<10) sseconds="0"+seconds; else
			 * sseconds=""+seconds;
			 * 
			 * return shours+":"+sminutes+":"+sseconds;
			 */
			return hours + ":" + minutes + ":" + seconds;
		} else
			return "";
	}

	@GetMapping("/timespentresbydaterange/{userid}/{courseid}")
	public List<LaContentTimespent> timespentresbyDaterange(
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "sessionid  pattern not matching ") String userid,
			@PathVariable @Pattern(regexp = "^([1-9][0-9]{0,2}|1000)$", message = "courseid  pattern not matching ") String courseid,
			@RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate) throws ParseException {
		// long milliseconds = 0;

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = sdf1.parse(fromdate + " 00:00:00");
		// java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
		Timestamp sqlStartDate = new Timestamp(date.getTime());
		java.util.Date date1 = sdf1.parse(todate + " 23:59:59");
		// java.sql.Date sqlEndDate = new java.sql.Date(date1.getTime());
		Timestamp sqlEndDate = new Timestamp(date1.getTime());

		// java.util.Date utilDate = new java.util.Date();
		// java.sql.Date sqlfromdate = new java.sql.Date(fromdate.getTime());
		// java.sql.Date sqltodate = new java.sql.Date(todate.getTime());

		List<LaContentTimespent> lconvisit = laContentVisitService.getByUserIdAndCourseIdAndDaterangetimediff(userid, courseid,sqlStartDate, sqlEndDate);
		
		/*
		 * List<LaContentTimespent> la = new ArrayList<LaContentTimespent>();
		 * 
		 * 
		 * if (lconvisit.size() != 0) { for (LaContentVisit l : lconvisit) {
		 * LaContentTimespent la1 = new LaContentTimespent();
		 * la1.setResTitle(l.getResTitle()); milliseconds = l.getOutTime().getTime() -
		 * l.getInTime().getTime(); int seconds = (int) milliseconds / 1000;
		 * 
		 * int hours = seconds / 3600; int minutes = (seconds % 3600) / 60; seconds =
		 * (seconds % 3600) % 60; String shours, sminutes, sseconds; if (hours < 10)
		 * shours = "0" + hours; else shours = "" + hours; if (minutes < 10) sminutes =
		 * "0" + minutes; else sminutes = "" + minutes; if (seconds < 10) sseconds = "0"
		 * + seconds; else sseconds = "" + seconds; la1.setSpentTime(shours + ":" +
		 * sminutes + ":" + sseconds); la.add(la1);
		 * 
		 * }
		 * 
		 * }
		 */

		return lconvisit;
	}

	@GetMapping("/timespentres/{userid}/{courseid}")
	public List<LaContentTimespent> timespentres(
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "sessionid  pattern not matching ") String userid,
			@PathVariable @Pattern(regexp = "^([1-9][0-9]{0,2}|1000)$", message = "courseid  pattern not matching ") String courseid) {
		long milliseconds = 0;
		List<LaContentVisit> lconvisit = laContentVisitService.getByUserIdAndCourseId(userid, courseid);
		List<LaContentTimespent> la = new ArrayList<LaContentTimespent>();
		// LaContentTimespent la1=new LaContentTimespent();

		if (lconvisit.size() != 0) {
			for (LaContentVisit l : lconvisit) {
				LaContentTimespent la1 = new LaContentTimespent();
				la1.setResTitle(l.getResTitle());
				milliseconds = l.getOutTime().getTime() - l.getInTime().getTime();
				int seconds = (int) milliseconds / 1000;

				int hours = seconds / 3600;
				int minutes = (seconds % 3600) / 60;
				seconds = (seconds % 3600) % 60;
				String shours, sminutes, sseconds;
				if (hours < 10)
					shours = "0" + hours;
				else
					shours = "" + hours;
				if (minutes < 10)
					sminutes = "0" + minutes;
				else
					sminutes = "" + minutes;
				if (seconds < 10)
					sseconds = "0" + seconds;
				else
					sseconds = "" + seconds;
				la1.setSpentTime(shours + ":" + sminutes + ":" + sseconds);
				la.add(la1);

			}

		}

		return la;
	}

	/*
	 * @GetMapping("/lauseractionbyemailid/{id}") public
	 * ResponseEntity<LaUserAction> get(@PathVariable Integer id) { try {
	 * LaUserAction la = laUserActionService.get(id); return new
	 * ResponseEntity<LaUserAction>(la, HttpStatus.OK); } catch
	 * (NoSuchElementException e) { return new
	 * ResponseEntity<LaUserAction>(HttpStatus.NOT_FOUND); } }
	 */
	// RESTful API method for Create operation
	@PostMapping("/lacontentvisitintime/{userid}/{courseid}/{resid}/{restitle}/{sess}/{filetype}")
	public ResponseEntity<?> add(
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "userid  pattern not matching ") String userid,
			@PathVariable @Pattern(regexp = "^([1-9][0-9]{0,2}|1000)$", message = "courseid  pattern not matching ") String courseid,
			@PathVariable String resid, @PathVariable String restitle,
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "sessionid  pattern not matching ") String sess,
			@PathVariable String filetype) {
		LaContentVisit la = new LaContentVisit();
		la.setCourseId(courseid);
		la.setUserId(userid);
		la.setResId(resid);
		la.setResTitle(restitle);
		la.setSessionId(sess);
		la.setFiletype(filetype);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		la.setInTime(timestamp);
		la.setOutTime(timestamp);

		laContentVisitService.save(la);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	// RESTful API method for Update operation
	// @PutMapping("/lacontentvisitouttime/{userid:[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}}/{sess}")
	@PutMapping("/lacontentvisitouttime/{userid}/{sess}")
	public ResponseEntity<?> update(@PathVariable String userid,
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "sessionid  pattern not matching ") String sess) {
		try {
			int sno = laContentVisitService.getMaxsno(userid, sess);

			// LaContentVisit existlaconvisit =
			// laContentVisitService.getByUserIdAndSession(userid,sess);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			int status = laContentVisitService.updateContentVisitTime(timestamp, userid, sno, sess);
			// existlaconvisit.setOutTime(timestamp);

			System.out.println("-------------" + status);

			// laContentVisitService.save(existlaconvisit);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NullPointerException e1) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	// RESTful API method for Delete operation
}