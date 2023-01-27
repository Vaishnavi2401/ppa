package com.cdac.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.keycloak.service.KeycloakAdminClientService;
import com.cdac.model.LearnerMaster;

import com.cdac.dto.AddRole;
import com.cdac.dto.AssignRole;
import com.cdac.dto.CreateUserRequest;

import com.cdac.dto.RegisterLearner;
import com.cdac.dto.RelieveUser;

import com.cdac.dto.UserMasterDTO;
import com.cdac.exception.InvalidDataException;

/**
 * We will be using
 * https://www.keycloak.org/docs-api/11.0.1/javadocs/org/keycloak/admin/client/resource/RolesResource.html
 * API.
 * 
 * @author Ramu Parupalli
 * 
 * @version 0.0.1
 * @since 0.0.1
 */

@CrossOrigin("*")
//@CrossOrigin(origins = "http://ngel1.hyderabad.cdac.in/*")
@RestController
@RequestMapping(path = "/um_auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class KeycloakController {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";
	public static final String ROLEASSIGNSUCCESS = "RoleAssignSuccess";

	@Autowired
	private KeycloakAdminClientService keycloakAdminClientService;





	@Autowired
	private LearnerController learnerController;

	/*
	 * @GetMapping(path = "/hello") public String hello() { return "Hello World!"; }
	 */

	@PostMapping(path = "/addkeycloakuser")
	public String createkeycloakUser(@RequestBody CreateUserRequest cr) throws InvalidDataException {
		return keycloakAdminClientService.createKeycloakUser(cr);
	}

	

	//@PostMapping(path = "/register")
	public String createLearner(@Valid @RequestBody RegisterLearner registerLearner) throws InvalidDataException {
		String status = null;
		LearnerMaster tm = null;
		// System.out.println("date format in keycloak
		// controller-------"+traineeMasterDTO.getDob());
		status = keycloakAdminClientService.createLearner(registerLearner);

		if (status != null && !status.equalsIgnoreCase("Exists") && !status.equalsIgnoreCase("FAILED")) {
			String keycloakid= keycloakAdminClientService.getUserId(registerLearner.getEmail());
				return keycloakid;
			} else {
				keycloakAdminClientService.removeLearner(tm.getLearnerUsername());
				return FAILED;
			}

		} 

	

	

	@PostMapping(path = "/assigninstrole")
	public String assignInstRole(@RequestBody AssignRole ar) throws InvalidDataException {

		System.out.println("inside controller");
		if (keycloakAdminClientService.assignClientInstRole(ar).equals(ROLEASSIGNSUCCESS)) {
		String status=learnerController.approveInstRequest(ar.getUsername());
			return status;
		} else {

			return FAILED;
		}
	}



	@PostMapping(path = "/addrole")
	public String addRole(@RequestBody AddRole adr) throws InvalidDataException {

		return keycloakAdminClientService.createRole(adr);
	}

	@GetMapping(path = "/getallroles")
	public Collection<RoleRepresentation> listRealmRoles() {
		for (int i = 0; i < keycloakAdminClientService.listRealmRoles().size(); i++) {
			System.out.println(
					"Role " + i + "-----------" + keycloakAdminClientService.listRealmRoles().get(i).toString());
		}
		return keycloakAdminClientService.listRealmRoles();
	}

	@GetMapping(path = "/getallclientroles")
	public List<String> listClientRoles() {
		List<String> roles = new ArrayList<>();
		for (int i = 0; i < keycloakAdminClientService.listClientRoles().size(); i++) {
			System.out.println(

					"Role " + i + "-----------" + keycloakAdminClientService.listClientRoles().get(i).toString());

			if (!keycloakAdminClientService.listClientRoles().get(i).toString().equals("tmisuser")
					&& !keycloakAdminClientService.listClientRoles().get(i).toString().equals("trainee")
					&& !keycloakAdminClientService.listClientRoles().get(i).toString().equals("dummyuser")
					&& !keycloakAdminClientService.listClientRoles().get(i).toString().equals("bprdsuperadmin")) {
				roles.add(keycloakAdminClientService.listClientRoles().get(i).toString());
			}

		}

		return roles;
	}

	@GetMapping(path = "/roles")
	public Collection<String> rolesOfCurrentUser() {
		return keycloakAdminClientService.getCurrentUserRoles();
	}

	@GetMapping(path = "/login1")
	public String rolesOfloginUser() {
		List<String> roles = keycloakAdminClientService.getLoginUserRoles();
		String role = null;
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).equalsIgnoreCase("ROLE_CDTIADMIN")) {
				role = "cdtiadmin";
			} else if (roles.get(i).equalsIgnoreCase("ROLE_TRAINEE")) {
				role = "trainee";
			} else if (roles.get(i).equalsIgnoreCase("ROLE_CDTIDIRECTOR")) {
				role = "cdtidirector";
			} else if (roles.get(i).equalsIgnoreCase("ROLE_BPRDSUPERADMIN")) {
				role = "bprdsuperadmin";
			} else if (roles.get(i).equalsIgnoreCase("ROLE_BPRDDG")) {
				role = "bprddg";
			} else if (roles.get(i).equalsIgnoreCase("ROLE_BPRDADMIN")) {
				role = "bprdadmin";
			}
		}
		return role;
	}

	@GetMapping(path = "/profile")
	public Object profileOfCurrentUser() {
		return keycloakAdminClientService.getUserProfileOfLoggedUser();
	}

	@GetMapping(path = "/bprdadmin")
	public String BprdadminHome() {
		// System.out.println("inside cdti admin");
		// keycloakAdminClientService.getLoginUserRoles();
		return "Welcome BPRD Admin";
	}

	@GetMapping(path = "/bprddg")
	public String BprddgHome() {
		// System.out.println("inside cdti admin");
		// keycloakAdminClientService.getLoginUserRoles();
		return "Welcome BPRD dG";
	}

	@GetMapping(path = "/bprdsuperadmin")
	public String BprdsuperadminHome() {
		// System.out.println("inside cdti admin");
		// keycloakAdminClientService.getLoginUserRoles();
		return "Welcome BPRD Super admin";
	}

	@GetMapping(path = "/cdtiadmin")
	public String CdtiadminHome() {
		// System.out.println("inside cdti admin");
		// keycloakAdminClientService.getLoginUserRoles();
		return "Welcome CDTI Admin User";
	}

	@GetMapping(path = "/trainee")
	public String TraineeHome() {
		// System.out.println("inside cdti admin");
		// keycloakAdminClientService.getLoginUserRoles();
		return "Welcome to Trainee Home";
	}

	@GetMapping(path = "/cdtidirector")
	public String CdtidirectorHome() {
		// System.out.println("inside cdti admin");
		// keycloakAdminClientService.getLoginUserRoles();
		return "Welcome CDTI Director Home";
	}

	@GetMapping(path = "/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "/";
	}

	@PostMapping(path = "/blockUser/{userId}")
	public Object blockUser(@PathVariable String userId) {
		return keycloakAdminClientService.blockUser(userId);
		// return response;
	}

	@PostMapping(path = "/enableUser/{userId}")
	public UserRepresentation enableUser(@PathVariable String userId) {
		UserRepresentation s =  keycloakAdminClientService.enableUser(userId);
		System.out.println(s.isEnabled());
		
		 return s;
	}

}
