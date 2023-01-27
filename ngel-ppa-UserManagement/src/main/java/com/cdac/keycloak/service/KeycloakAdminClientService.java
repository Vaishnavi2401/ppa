package com.cdac.keycloak.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.util.JsonSerialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.cdac.exception.InvalidDataException;
import com.cdac.keycloak.KeycloakAdminClientConfig;
import com.cdac.keycloak.KeycloakAdminClientUtils;
import com.cdac.keycloak.KeycloakPropertyReader;
import com.cdac.model.LearnerMaster;
import com.cdac.dao.LearnerDAO;
import com.cdac.dto.AddRole;
import com.cdac.dto.AssignRole;
import com.cdac.dto.CreateUserRequest;
import com.cdac.dto.LearnerMasterDTO;
import com.cdac.dto.RelieveUser;
import com.cdac.dto.RegisterLearner;
import com.cdac.security.CurrentUserProvider;
import com.cdac.util.EmailSender;
import com.cdac.util.RandomPasswordGenerator;
import com.cdac.dto.UserDTO;
import com.cdac.dto.UserMasterDTO;

/**
 * @author Ramu Parupalli
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@Configuration
@PropertySource("classpath:email.properties")
@PropertySource("classpath:application.properties")
public class KeycloakAdminClientService {

	public static final String SUCCESS = "Success";
	public static final String EXISTS = "Exists";
	public static final String FAILED = "Failed";
	public static final String ROLEASSIGNFAILED = "RoleAssignFailed";
	public static final String ROLEASSIGNSUCCESS = "RoleAssignSuccess";
	public static final String ROLEREMOVEDSUCCESS = "RoleRemovedSuccess";
	public static final String ROLEREMOVEDFAILED = "RoleRemovedFailed";

	@Value("${keycloak.resource}")
	private String keycloakClient;

	@Autowired
	private CurrentUserProvider currentUserProvider;

	@Autowired
	private KeycloakPropertyReader keycloakPropertyReader;

	@Autowired
	private AddRole addRole;

	@Autowired
	private AssignRole ar;

	@Autowired
	private RandomPasswordGenerator rpg;

	@Autowired
	private EmailSender eMail;

	@Value("${mail.subject}")
	private String subject;

	@Value("${mail.subject1}")
	private String subject1;

	@Value("${mail.body.registartion}")
	private String bodytext;

	@Value("${mail.body.registartion1}")
	private String bodytext1;

	Keycloak keycloak;

	@Value("${keycloak.auth-server-url}")
	String serverUrl;

	@Value("${keycloak.realm}")
	String realm;

	@Value("${keycloak.resource}")
	String clientId;

	@Value("${keycloak.credentials.secret}")
	String clientSecret;

	@Autowired
	private LearnerDAO learnerDAO;

	public List<String> getCurrentUserRoles() {
		return currentUserProvider.getCurrentUser().getRoles();
	}

	public void generateKeyCloak() {

		// System.out.println("------"+serverUrl);
		keycloak = KeycloakBuilder.builder() //
				.serverUrl(serverUrl) //
				.realm(realm) //
				.grantType(OAuth2Constants.PASSWORD) //
				.clientId(clientId) //
				.clientSecret(clientSecret) //
				.username("clientadmin") //
				.password("Cdac@123$") //
				.build();
	}

	public List<String> getLoginUserRoles() {

		@SuppressWarnings("unchecked")
		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		KeycloakAdminClientConfig keycloakAdminClientConfig = KeycloakAdminClientUtils
				.loadConfig(keycloakPropertyReader);
		/*
		 * Keycloak keycloak = KeycloakAdminClientUtils.getKeycloakClient(principal.
		 * getKeycloakSecurityContext(), keycloakAdminClientConfig);
		 */
		// Get realm
		System.out.println("Realm in getloginuser roles------------------>" + keycloakAdminClientConfig.getRealm());
		RealmResource realmResource = keycloak.realm(keycloakAdminClientConfig.getRealm());
		UsersResource usersResource = realmResource.users();
		UserResource userResource = usersResource.get(currentUserProvider.getCurrentUser().getUserId());
		UserRepresentation userRepresentation = userResource.toRepresentation();

		return currentUserProvider.getCurrentUser().getRoles();
	}

	public Object getUserProfileOfLoggedUser() {

		@SuppressWarnings("unchecked")
		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		KeycloakAdminClientConfig keycloakAdminClientConfig = KeycloakAdminClientUtils
				.loadConfig(keycloakPropertyReader);
		Keycloak keycloak = KeycloakAdminClientUtils.getKeycloakClient(principal.getKeycloakSecurityContext(),
				keycloakAdminClientConfig);

		// Get realm
		System.out.println("Realm in profile------------------>" + keycloakAdminClientConfig.getRealm());
		RealmResource realmResource = keycloak.realm(keycloakAdminClientConfig.getRealm());
		UsersResource usersResource = realmResource.users();
		UserResource userResource = usersResource.get(currentUserProvider.getCurrentUser().getUserId());
		UserRepresentation userRepresentation = userResource.toRepresentation();

		return userRepresentation;
	}

	public String createKeycloakUser(CreateUserRequest cr) throws InvalidDataException {

		try {

			generateKeyCloak(); // making keycloak connection
			String realmname = realm;

			CredentialRepresentation credential = getCredentialRepresentation(cr.getPassword(), true);
			UserRepresentation user = getNewUserRepresentation(cr, credential);
			user.setEnabled(Boolean.TRUE);
			// System.out.println("Realm------------------>"+keycloakAdminClientConfig.getRealm());
			// System.out.println("user-------------->"+user.getEmail());

			Response response = keycloak.realm(realmname).users().create(user);
			if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
				// System.out.println("status code----------"+response.getStatus());
				// System.out.println(user + " was created.");
				return SUCCESS;
			} else if (response.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
				// System.out.println("status code----------"+response.getStatus());
				// System.out.println(user + " has already been created.");
				return EXISTS;
			} else {
				// System.out.println("Result status: " + response.getStatus());
				return FAILED;
			}

		} catch (ClientErrorException e) {
			return FAILED;

		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof ClientErrorException) {
				return FAILED;
			} else {
				return FAILED;
			}
		}
		// UserDTO newUser = buildResponse(request, keycloak);
		// return "User Addedd successfully";
	}

	public String createTmisUser(UserMasterDTO userMasterdto) throws InvalidDataException {

		try {

			generateKeyCloak(); // making keycloak connection

			String pass = rpg.generateRandomPassword(8);
			// System.out.println("password---------------------->"+pass);
			CredentialRepresentation credential = getCredentialRepresentation(pass, true);

			// CredentialRepresentation credential = getCredentialRepresentation("tmis@123",
			// true);
			UserRepresentation user = getNewTMISUserRepresentation(userMasterdto, credential);
			user.setEnabled(Boolean.TRUE);
			// System.out.println("Realm------------------>"+keycloakAdminClientConfig.getRealm());
			// System.out.println("user-------------->"+user.getEmail());

			Response response = keycloak.realm(realm).users().create(user);

			// resultstatus=response.getStatus();
			if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
				String userId = CreatedResponseUtil.getCreatedId(response);
				// System.out.println(user + " was created.");
				String formattedtext = MessageFormat.format(bodytext,
						userMasterdto.getFirstName() + " " + userMasterdto.getLastName(), pass);
				// System.out.println("----------------subject and bodytext in keycloak
				// after------"+subject+" "+bodytext);
				try {

					eMail.sendEmail(userMasterdto.getEmail(), subject, formattedtext);
					// System.out.println("emaicdac@123"
					// + "l sent -------------->"+user.getEmail());
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

				return userId;
			} else if (response.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
				// System.out.println(user + " has already been created.");
				return EXISTS;
			} else {
				// System.out.println("Result status: " + response.getStatus());
				return FAILED;
			}

		} catch (ClientErrorException e) {
			return FAILED;

		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof ClientErrorException) {
				return FAILED;
			} else {
				return FAILED;
			}
		}
		// UserDTO newUser = buildResponse(request, keycloak);

	}

	public String removeTmisUser(String userId) throws InvalidDataException {

		try {

			generateKeyCloak(); // making keycloak connection

			keycloak.realm(realm).users().get(userId).remove();

			return SUCCESS;

		} catch (ClientErrorException e) {
			return FAILED;

		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof ClientErrorException) {
				return FAILED;
			} else {
				return FAILED;
			}
		}
	}

	public String createLearner(RegisterLearner registerLearner) throws InvalidDataException {

		generateKeyCloak(); // making keycloak connection

		// int resultstatus=0;
		try {

			String pass = rpg.generateRandomPassword(8);
			CredentialRepresentation credential = getCredentialRepresentation(pass, true);

			UserRepresentation user = getNewLearnerRepresentation(registerLearner, credential);
			user.setEnabled(Boolean.TRUE);
			// System.out.println("Realm------------------>"+keycloakAdminClientConfig.getRealm());
			System.out.println("user-------------->" + user.getEmail());

			Response response = keycloak.realm(realm).users().create(user);
			System.out.println("-------------------------------" + response.getStatus());
			// resultstatus=response.getStatus();
			if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
				// System.out.println(user + " was created.");
				// ar.setRealmname(realmname);
				ar.setRolename("learner");
				// System.out.println("--------------------"+registerLearner.getLearnerUsername());
				System.out.println("--------------------"+registerLearner.getEmail());
				ar.setUsername(registerLearner.getEmail());

				// String status = assignClientLearnerRole(ar);
				
				// assigining both client and releam role learner role to user for protecting REST API end points
				String rrstatus = assignRealmLearnerRole(ar);
				
				String crstatus=assignClientLearnerRole(ar);

				System.out.println("Learner Role Status----" + rrstatus);
				String userId = CreatedResponseUtil.getCreatedId(response);
				System.out.println("Learner Role Assigned successfully" + userId);
				if (rrstatus == ROLEASSIGNSUCCESS && crstatus==ROLEASSIGNSUCCESS) {
					String formattedtext = MessageFormat.format(bodytext,
							registerLearner.getFirstName() + " " + registerLearner.getLastName(), pass);
					// System.out.println("----------------subject and bodytext in keycloak
					// after------"+subject+" "+bodytext);
					try {

						eMail.sendEmail(registerLearner.getEmail(), subject, formattedtext);
						// System.out.println("emaicdac@123"
						// + "l sent -------------->"+user.getEmail());
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					return userId;
				} else {
					return FAILED;
				}

			} else if (response.getStatus() == Response.Status.CONFLICT.getStatusCode()) {

				return EXISTS;
			} else {
				// System.out.println("Result status: " + response.getStatus());
				return FAILED;
			}

		} catch (ClientErrorException e) {
			e.printStackTrace();
			return FAILED;
		} catch (Exception e) {
			e.printStackTrace();
			Throwable cause = e.getCause();
			if (cause instanceof ClientErrorException) {
				return FAILED;
			} else {
				return FAILED;
			}
		}
		// UserDTO newUser = buildResponse(request, keycloak);

	}

	
	public String updateLearnerName(LearnerMaster lm) {
		try {

			generateKeyCloak(); // making keycloak connection

			RealmResource realmResource = keycloak.realm(realm);
		
		UsersResource usersResource = realmResource.users();
		
		
		 UserResource retrievedUser = keycloak.realm(realm).users().get(lm.getLearnerUsername());
	      
	        UserRepresentation userRepresentation = retrievedUser.toRepresentation ();
	       
	            userRepresentation.setFirstName(lm.getFirstName());
	            userRepresentation.setLastName(lm.getLastName());
	                  
	        retrievedUser.update (userRepresentation);
	        return "success";
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "error";
		}
	}
	
	public String createMeghUser(RegisterLearner registerLearner) throws InvalidDataException {

		generateKeyCloak(); // making keycloak connection

		// int resultstatus=0;
		try {

			String pass = rpg.generateRandomPassword(8);
			CredentialRepresentation credential = getCredentialRepresentation(pass, true);

			UserRepresentation user = getNewLearnerRepresentation(registerLearner, credential);
			user.setEnabled(Boolean.TRUE);
			// System.out.println("Realm------------------>"+keycloakAdminClientConfig.getRealm());
			System.out.println("user-------------->" + user.getEmail());

			Response response = keycloak.realm(realm).users().create(user);

			System.out.println("-------------------------------" + response.getStatus());
			// resultstatus=response.getStatus();
			if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
				// System.out.println(user + " was created.");
				// ar.setRealmname(realmname);
				ar.setRolename("instructor");
				// System.out.println("--------------------"+registerLearner.getLearnerUsername());
				ar.setUsername(registerLearner.getEmail());

				// String status = assignClientLearnerRole(ar);
				String status = assignRealmLearnerRole(ar);

				System.out.println("Instructor Role Status----" + status);
				String userId = CreatedResponseUtil.getCreatedId(response);
				System.out.println("Instructor Role Assigned successfully" + userId);
				if (status == ROLEASSIGNSUCCESS) {
					String formattedtext = MessageFormat.format(bodytext,
							registerLearner.getFirstName() + " " + registerLearner.getLastName(), pass);
					// System.out.println("----------------subject and bodytext in keycloak
					// after------"+subject+" "+bodytext);
					try {

						eMail.sendEmail(registerLearner.getEmail(), subject, formattedtext);
						// System.out.println("emaicdac@123"
						// + "l sent -------------->"+user.getEmail());
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					return userId;
				} else {
					return FAILED;
				}

			} else if (response.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
				keycloak.realm(realm).users().search(user.getEmail()).get(0).getId();
				// System.out.println("--------------UserID------------"+response.get);
				return EXISTS;
			} else {
				// System.out.println("Result status: " + response.getStatus());
				return FAILED;
			}

		} catch (ClientErrorException e) {
			e.printStackTrace();
			return FAILED;
		} catch (Exception e) {
			e.printStackTrace();
			Throwable cause = e.getCause();
			if (cause instanceof ClientErrorException) {
				return FAILED;
			} else {
				return FAILED;
			}
		}
		// UserDTO newUser = buildResponse(request, keycloak);

	}

	public String getUserId(String email) {
		generateKeyCloak();
		try {

			String userid = keycloak.realm(realm).users().search(email).get(0).getId();
			return userid;
		} catch (Exception e) {
			return FAILED;
		}

	}

	public String removeLearner(String userId) throws InvalidDataException {

		generateKeyCloak(); // making keycloak connection

		try {

			keycloak.realm(realm).users().get(userId).remove();

			return SUCCESS;

		} catch (ClientErrorException e) {
			return FAILED;

		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof ClientErrorException) {
				return FAILED;
			} else {
				return FAILED;
			}
		}
	}

	public String createRole(AddRole adr) {

		generateKeyCloak(); // making keycloak connection

		try {
			RoleRepresentation roleRepresentation = new RoleRepresentation();
			roleRepresentation.setName(adr.getRoleName());
			roleRepresentation.setClientRole(true);
			keycloak.realm(realm).roles().create(roleRepresentation);
			// System.out.println(adr.getRoleName() + " was created.");
			return SUCCESS;

		} catch (ClientErrorException e) {
			if (e.getResponse().getStatus() == Response.Status.CONFLICT.getStatusCode()) {
				// System.out.println(addRole.getRoleName() + " has already been created.");
				return EXISTS;
			} else {
				// handleClientErrorException(e);
				return FAILED;
			}
			// return "Role creation failed";
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof ClientErrorException) {
				// handleClientErrorException((ClientErrorException) cause);
				return FAILED;
			} else {
				// e.printStackTrace();
				return FAILED;
			}
			// return "Role creation failed";
		}
		// return "Role created Successfully";
	}

	public String assignAdminRole(AssignRole ar) {

		try {

			generateKeyCloak(); // making keycloak connection

			RealmResource realmResource = keycloak.realm(realm);

			List<UserRepresentation> retrieveCreatedUserList = keycloak.realm(realm).users().search(ar.getUsername(),
					null, null, null, 0, 1);
			UserResource retrievedUser = keycloak.realm(realm).users().get(retrieveCreatedUserList.get(0).getId());

			// Get client
			ClientRepresentation app1Client = realmResource.clients() //
					.findByClientId(clientId).get(0);

			// Get client level role (requires view-clients role)
			RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()) //
					.roles().get(ar.getRolename()).toRepresentation();
			RoleRepresentation userRole = realmResource.clients().get(app1Client.getId()) //
					.roles().get("dummyuser").toRepresentation();

			retrievedUser.roles() //
					.clientLevel(app1Client.getId()).remove(Arrays.asList(userRole));

			// Assign client level role to user
			retrievedUser.roles() //
					.clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}
	}

	public String assignClientLearnerRole(AssignRole ar) {
		try {

			generateKeyCloak(); // making keycloak connection

			RealmResource realmResource = keycloak.realm(realm);

			// Get realm
			// RealmResource realmResource = keycloak.realm(assignRole.getRealmname());
			UsersResource usersResource = realmResource.users();
			String userId = usersResource.get(ar.getUsername()).toString();
			List<UserRepresentation> retrieveCreatedUserList = keycloak.realm(realm).users().search(ar.getUsername(),
					null, null, null, 0, 1);
			UserResource retrievedUser = keycloak.realm(realm).users().get(retrieveCreatedUserList.get(0).getId());

			// Get client
			ClientRepresentation app1Client = realmResource.clients() //
					.findByClientId(clientId).get(0);

			System.out.println("clientid-------------------------" + app1Client);

			// Get client level role (requires view-clients role)
			RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()) //
					.roles().get("learner").toRepresentation();

			System.out.println("user client role-------------------------" + userClientRole.getName().toString());

			// Assign client level role to user
			retrievedUser.roles() //
					.clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

			return ROLEASSIGNSUCCESS;

		} catch (Exception e) {
			return ROLEASSIGNFAILED;
		}

	}

	public String assignClientInstRole(AssignRole ar) {
		try {
			System.out.println("inside inst role");
			generateKeyCloak(); // making keycloak connection
			//System.out.println("---------------------"+ar.getUsername());
			RealmResource realmResource = keycloak.realm(realm);
									
			// Get realm
			// RealmResource realmResource = keycloak.realm(assignRole.getRealmname());
			UsersResource usersResource = realmResource.users();
			
			UserResource userResource = usersResource.get(ar.getUsername());
			UserRepresentation userRepresentation = userResource.toRepresentation();
			
			
			String userId = usersResource.get(ar.getUsername()).toString();
			List<UserRepresentation> retrieveCreatedUserList = keycloak.realm(realm).users().search(userRepresentation.getEmail(),
					null, null, null, 0, 1);
			//System.out.println("---------------------"+retrieveCreatedUserList.size());
			UserResource retrievedUser = keycloak.realm(realm).users().get(retrieveCreatedUserList.get(0).getId());

			// Get client
			ClientRepresentation app1Client = realmResource.clients() //
					.findByClientId(clientId).get(0);

			System.out.println("clientid-------------------------" + app1Client);

			// Get client level role (requires view-clients role)
			RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()) //
					.roles().get("instructor").toRepresentation();

			//System.out.println("user client role-------------------------" + userClientRole.getName().toString());

			// Assign client level role to user
			retrievedUser.roles() //
					.clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

			List<LearnerMaster> learnerObj = learnerDAO.findByEmail(ar.getUsername());
			for (LearnerMaster learnerMaster : learnerObj) {
				String formattedtext = MessageFormat.format(bodytext1,
						learnerMaster.getFirstName() + " " + learnerMaster.getLastName());
				// System.out.println("----------------subject and bodytext in keycloak
				// after------"+subject+" "+bodytext);
				try {
					eMail.sendEmail(learnerMaster.getEmail(), subject1, formattedtext);
					// System.out.println("emaicdac@123"
					// + "l sent -------------->"+user.getEmail());
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

			}

			return ROLEASSIGNSUCCESS;

		} catch (

		Exception e) {
			e.printStackTrace();
			return ROLEASSIGNFAILED;
		}

	}

	public String assignRealmLearnerRole(AssignRole ar) {
		try {

			generateKeyCloak(); // making keycloak connection

			RealmResource realmResource = keycloak.realm(realm);

			// Get realm
			// RealmResource realmResource = keycloak.realm(assignRole.getRealmname());
			UsersResource usersResource = realmResource.users();
			String userId = usersResource.get(ar.getUsername()).toString();
			List<UserRepresentation> retrieveCreatedUserList = keycloak.realm(realm).users().search(ar.getUsername(),
					null, null, null, 0, 1);
			UserResource retrievedUser = keycloak.realm(realm).users().get(retrieveCreatedUserList.get(0).getId());

			/*
			 * // Get client ClientRepresentation app1Client = realmResource.clients() //
			 * .findByClientId(clientId).get(0);
			 * 
			 * System.out.println("clientid-------------------------" + app1Client);
			 * 
			 * // Get client level role (requires view-clients role) RoleRepresentation
			 * userClientRole = realmResource.clients().get(app1Client.getId()) //
			 * .roles().get("learner").toRepresentation();
			 * 
			 * System.out.println("user client role-------------------------" +
			 * userClientRole.getName().toString());
			 * 
			 */

			RoleRepresentation rp = realmResource.roles().get("learner").toRepresentation();

			retrievedUser.roles().realmLevel().add(Arrays.asList(rp));

			// Assign client level role to user
			// retrievedUser.roles() //
			// .clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

			return ROLEASSIGNSUCCESS;

		} catch (Exception e) {
			return ROLEASSIGNFAILED;
		}

	}
	

	public String relieveUser(RelieveUser ru) {
		try {

			generateKeyCloak(); // making keycloak connection
			RealmResource realmResource = keycloak.realm(realm);

			// Get realm
			// RealmResource realmResource = keycloak.realm(assignRole.getRealmname());
			UsersResource usersResource = realmResource.users();
			String userId = usersResource.get(ru.getUsername()).toString();
			List<UserRepresentation> retrieveCreatedUserList = keycloak.realm(realm).users().search(ru.getUsername(),
					null, null, null, 0, 1);
			UserResource retrievedUser = keycloak.realm(realm).users().get(retrieveCreatedUserList.get(0).getId());

			// UserResource userResource = usersRessource.get(userId);

			// System.out.println("clientid-------------------------" +
			// keycloakAdminClientConfig.getClientId());

			// Get client
			ClientRepresentation app1Client = realmResource.clients() //
					.findByClientId(clientId).get(0);

			// Get client level role (requires view-clients role)
			RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()) //
					.roles().get("dummyuser").toRepresentation();

			RoleRepresentation userClientRoletoremove = realmResource.clients().get(app1Client.getId()) //
					.roles().get(ru.getExistingrolename()).toRepresentation();

			retrievedUser.roles() //
					.clientLevel(app1Client.getId()).remove(Arrays.asList(userClientRoletoremove));

			// Assign client level role to user
			retrievedUser.roles() //
					.clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

			return ROLEREMOVEDSUCCESS;

		} catch (Exception e) {
			return ROLEREMOVEDFAILED;
		}

	}

	public String removeRole(AssignRole ar) {

		generateKeyCloak(); // making keycloak connection
		RealmResource realmResource = keycloak.realm(realm);

		// Get realm
		// RealmResource realmResource = keycloak.realm(assignRole.getRealmname());
		UsersResource usersResource = realmResource.users();
		String userId = usersResource.get(ar.getUsername()).toString();
		List<UserRepresentation> retrieveCreatedUserList = keycloak.realm(realm).users().search(ar.getUsername(), null,
				null, null, 0, 1);
		UserResource retrievedUser = keycloak.realm(realm).users().get(retrieveCreatedUserList.get(0).getId());

//    	        // Get client
		ClientRepresentation app1Client = realmResource.clients() //
				.findByClientId(clientId).get(0);
		//
//    	        // Get client level role (requires view-clients role)
		RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()) //
				.roles().get(ar.getRolename()).toRepresentation();
		//
//    	        // Assign client level role to user
		retrievedUser.roles() //
				.clientLevel(app1Client.getId()).remove(Arrays.asList(userClientRole));

		// Send password reset E-Mail
		// VERIFY_EMAIL, UPDATE_PROFILE, CONFIGURE_TOTP, UPDATE_PASSWORD,
		// TERMS_AND_CONDITIONS
//    	        usersRessource.get(userId).executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));

		// Delete User
//    	        userResource.remove();

		return "role removed successfully";
	}

	private UserDTO buildResponse(CreateUserRequest request, Keycloak kcMaster, String realm)
			throws InvalidDataException {

		return kcMaster.realms().realm(realm).users().search(request.getUserName()).stream()
				.filter(u -> u.getUsername().equals(request.getUserName())).findFirst().map(this::convertToUserDTO)
				.orElseThrow(InvalidDataException::new);
	}

	private UserDTO convertToUserDTO(UserRepresentation userRepresentation) {

		UserDTO userDTO = new UserDTO();
		userDTO.setId(userRepresentation.getId());
		userDTO.setUserName(userRepresentation.getUsername());
		userDTO.setFirstName(userRepresentation.getFirstName());
		userDTO.setLastName(userRepresentation.getLastName());
		userDTO.setEmail(userRepresentation.getEmail());
		return userDTO;
	}

	private CredentialRepresentation getCredentialRepresentation(String password, boolean isTempPassword) {
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(password);
		credential.setTemporary(isTempPassword);
		return credential;
	}

	private UserRepresentation getNewUserRepresentation(CreateUserRequest userCreationParam,
			CredentialRepresentation credential) {
		UserRepresentation user = new UserRepresentation();
		// System.out.println("user-----------"+userCreationParam.getUserName());
		user.setUsername(userCreationParam.getUserName());
		user.setFirstName(userCreationParam.getFirstName());
		user.setLastName(userCreationParam.getLastName());
		user.setEmail(userCreationParam.getEmail());
		ArrayList<CredentialRepresentation> credentials = new ArrayList<>();
		credentials.add(credential);
		user.setCredentials(credentials);
		// user.setRequiredActions(Arrays.asList(ACTION_UPDATE_PASSWORD));
		user.setEnabled(true);
		return user;
	}

	private UserRepresentation getNewTMISUserRepresentation(UserMasterDTO userMasterdto,
			CredentialRepresentation credential) {
		UserRepresentation user = new UserRepresentation();
		// System.out.println("user-----------"+userCreationParam.getUserName());
		user.setUsername(userMasterdto.getUsername());
		user.setFirstName(userMasterdto.getFirstName());
		user.setLastName(userMasterdto.getLastName());
		user.setEmail(userMasterdto.getEmail());
		user.setAttributes(
				Collections.singletonMap("orgId", Arrays.asList(Integer.toString(userMasterdto.getOrgId()))));

		ArrayList<CredentialRepresentation> credentials = new ArrayList<>();
		credentials.add(credential);
		user.setCredentials(credentials);
		// user.setRequiredActions(Arrays.asList(ACTION_UPDATE_PASSWORD));
		user.setEnabled(true);
		return user;
	}

	private UserRepresentation getNewLearnerRepresentation(RegisterLearner registerLearner,
			CredentialRepresentation credential) {
		UserRepresentation user = new UserRepresentation();
		// System.out.println("user-----------"+registerLearner.getLearnerUsername());
		user.setUsername(registerLearner.getEmail());
		user.setFirstName(registerLearner.getFirstName());
		user.setLastName(registerLearner.getLastName());
		user.setEmail(registerLearner.getEmail());
		ArrayList<CredentialRepresentation> credentials = new ArrayList<>();
		credentials.add(credential);
		user.setCredentials(credentials);
		// user.setRequiredActions(Arrays.asList(ACTION_UPDATE_PASSWORD));
		user.setEnabled(true);
		return user;
	}

	@SuppressWarnings("rawtypes")
	private static void handleClientErrorException(ClientErrorException e) {
		e.printStackTrace();
		Response response = e.getResponse();
		try {
			System.out.println("status: " + response.getStatus());
			// System.out.println("reason: " + response.getStatusInfo().getReasonPhrase());
			Map error = JsonSerialization.readValue((ByteArrayInputStream) response.getEntity(), Map.class);
			System.out.println("error: " + error.get("error"));
			System.out.println("error_description: " + error.get("error_description"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public List<RoleRepresentation> listRealmRoles() {
		@SuppressWarnings("unchecked")
		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		KeycloakAdminClientConfig keycloakAdminClientConfig = KeycloakAdminClientUtils
				.loadConfig(keycloakPropertyReader);
		Keycloak keycloak = KeycloakAdminClientUtils.getKeycloakClient(principal.getKeycloakSecurityContext(),
				keycloakAdminClientConfig);
		RealmResource realmResource = keycloak.realm(keycloakAdminClientConfig.getRealm());
		// String realmname=keycloakAdminClientConfig.getRealm();
		List<RoleRepresentation> rolelist = realmResource.roles().list();

		return rolelist;
	}

	public List<RoleRepresentation> listClientRoles() {
		@SuppressWarnings("unchecked")
		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		KeycloakAdminClientConfig keycloakAdminClientConfig = KeycloakAdminClientUtils
				.loadConfig(keycloakPropertyReader);
		Keycloak keycloak = KeycloakAdminClientUtils.getKeycloakClient(principal.getKeycloakSecurityContext(),
				keycloakAdminClientConfig);
		RealmResource realmResource = keycloak.realm(keycloakAdminClientConfig.getRealm());

		ClientRepresentation app1Client = realmResource.clients() //
				.findByClientId(keycloakAdminClientConfig.getClientId()).get(0);

		List<RoleRepresentation> rolelist = realmResource.clients().get(app1Client.getId()).roles().list();

		return rolelist;
	}

	public Set<UserRepresentation> getInstructorsList() {

		generateKeyCloak(); // making keycloak connection

		// Get realm

		Set<UserRepresentation> usersOfRole = null;

		String roleName = "instructor";
		RealmResource realmResource = keycloak.realm(realm);
		// Get client
		ClientRepresentation app1Client = realmResource.clients().findByClientId(clientId).get(0);

		usersOfRole = realmResource.clients().get(app1Client.getId()).roles().get(roleName).getRoleUserMembers();

		return usersOfRole;
	}

	public UserRepresentation blockUser(String userId) {
		// String result = "error";
		UserRepresentation user = null;
		try {

			generateKeyCloak(); // making keycloak connection

			// get user resource
			RealmResource realmResource = keycloak.realm(realm);

			UsersResource userRessource = realmResource.users();

			// fetch an existing user
			user = userRessource.get(userId).toRepresentation();

			// change user
			user.setEnabled(false);

			// update
			userRessource.get(userId).update(user);
			System.out.println(user.isEnabled());
			// result = "success";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public UserRepresentation enableUser(String userId) {
		// String result = "error";
		UserRepresentation user = null;
		try {

			generateKeyCloak(); // making keycloak connection

			// get user resource
			RealmResource realmResource = keycloak.realm(realm);

			UsersResource userRessource = realmResource.users();

			// fetch an existing user
			user = userRessource.get(userId).toRepresentation();

			// change user
			user.setEnabled(true);

			// update
			userRessource.get(userId).update(user);
			// result = "success";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

}