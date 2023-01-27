package com.cdac.Controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.cdac.Entity.LaUserAction;
import com.cdac.Services.LaUserActionService;
import com.cdac.Utils.LaUserActionPojo;

@CrossOrigin("*")
@RequestMapping(value = "learning_analytics")
@RestController
public class LaUserActionController {

	@Autowired
	private LaUserActionService laUserActionService;

	// RESTful API methods for Retrieval operations
	@GetMapping("/lauseraction")
	public List<LaUserAction> list() {
		return laUserActionService.listAll();
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
	@PostMapping("/lauseractionlogin/{browser}/{os}/{resolution}/{userid}/{ip}/{sess}")
	public void add(@PathVariable String browser, @PathVariable String os, @PathVariable String resolution,
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "userid  pattern not matching ") String userid,
			@PathVariable String ip,
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "sessionid  pattern not matching ") String sess) {
		LaUserAction la = new LaUserAction();
		la.setConfig_browser(browser);
		la.setConfig_os(os);
		la.setConfig_resolution(resolution);
		la.setUserId(userid);
		la.setIp_address(ip);
		la.setSessionId(sess);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		la.setVisit_last_action_time(timestamp);
		la.setLogout(timestamp);
		la.setAction("Login");
		la.setSite_id(1);

		laUserActionService.save(la);
	}

	// RESTful API method for Update operation
	@PutMapping("/lauseractionlogout/{userid}/{sess}")
	public ResponseEntity<?> update(
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "userid  pattern not matching ") String userid,
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "sessionid  pattern not matching ") String sess) {
		try {
			int sno = laUserActionService.getMaxsno(userid, sess);
			// LaUserAction existProduct =
			// laUserActionService.getByUserIdAndSession(userid,sess);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			int status = laUserActionService.updateUserLogoutTime(timestamp, userid, sno, sess);
			// existProduct.setLogout(timestamp);

			// existProduct.setAction("Logout");

			// laUserActionService.save(existProduct);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NullPointerException e1) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	// RESTful API method for Delete operation

	@GetMapping("/activitylogbydaterange/{userid}")
	public List<LaUserActionPojo> activitylogbyDaterange(
			@PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", message = "userid  pattern not matching ") String userid,
			@RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate) throws ParseException {
		long milliseconds = 0;

		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// Date sqlfromdate = formatter.parse(fromdate);
		// Date sqltodate = formatter.parse(todate);

		// java.util.Date utilDate = new java.util.Date();
		// java.sql.Date sqlfromdate = new java.sql.Date(fromdate.getTime());
		// java.sql.Date sqltodate = new java.sql.Date(todate.getTime());

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = sdf1.parse(fromdate + " 00:00:00"); 
		Timestamp sqlStartDate = new Timestamp(date.getTime());
		java.util.Date date1 = sdf1.parse(todate + " 23:59:59");
		Timestamp sqlEndDate = new Timestamp(date1.getTime());
		todate = todate + " 23:59:59";
		
		List<LaUserAction> lauseraction = laUserActionService.findByUserIdandDaterange(userid, sqlStartDate, sqlEndDate);
		List<LaUserActionPojo> la = new ArrayList<LaUserActionPojo>();
		LaUserActionPojo la1 = null;
		
		for (LaUserAction l : lauseraction) {
			la1 = new LaUserActionPojo();
			la1.setUserId(l.getUserId());
			System.out.println(l.getVisit_last_action_time() + "===" + l.getLogout());
			la1.setVisit_last_action_time(l.getVisit_last_action_time().toString());
			la1.setLogout(l.getLogout().toString());
			la1.setAction(l.getAction());
			la1.setIp_address(l.getIp_address());
			la1.setConfig_browser(l.getConfig_browser());
			la1.setConfig_os(l.getConfig_os());
			la1.setConfig_resolution(l.getConfig_resolution());

			la.add(la1);
		}

		return la;
	}

}