package com.cdac.coursecatalouge.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user_roles database table.
 * 
 */
@Entity
@Table(name="user_roles")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int roleID;

	@Column(name="role_access")
	private String roleAccess;

	//bi-directional many-to-one association to UserTenantCourseMap
	@OneToMany(mappedBy="userRole")
	private List<UserTenantCourseMap> userTenantCourseMaps;

	public UserRole() {
	}

	public int getRoleID() {
		return this.roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleAccess() {
		return this.roleAccess;
	}

	public void setRoleAccess(String roleAccess) {
		this.roleAccess = roleAccess;
	}

	public List<UserTenantCourseMap> getUserTenantCourseMaps() {
		return this.userTenantCourseMaps;
	}

	public void setUserTenantCourseMaps(List<UserTenantCourseMap> userTenantCourseMaps) {
		this.userTenantCourseMaps = userTenantCourseMaps;
	}

	public UserTenantCourseMap addUserTenantCourseMap(UserTenantCourseMap userTenantCourseMap) {
		getUserTenantCourseMaps().add(userTenantCourseMap);
		userTenantCourseMap.setUserRole(this);

		return userTenantCourseMap;
	}

	public UserTenantCourseMap removeUserTenantCourseMap(UserTenantCourseMap userTenantCourseMap) {
		getUserTenantCourseMaps().remove(userTenantCourseMap);
		userTenantCourseMap.setUserRole(null);

		return userTenantCourseMap;
	}

}