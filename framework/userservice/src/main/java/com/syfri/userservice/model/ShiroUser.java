package com.syfri.userservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义Shiro用户
 */
public class ShiroUser implements Serializable{

	private static final long serialVersionUID = 1L;

	/*用户id.*/
	private String userid;

	/*username.*/
	private String username;

	/*realname.*/
	private String realName;

	/*角色.*/
	private List<String> roles;

	/*权限.*/
	private List<String> permissions;

	/*资源树.*/
	private List<ResourceTree> resourceTrees;

	public ShiroUser() {
	}

	public ShiroUser(String userid, String username, String realName) {
		this.userid = userid;
		this.username = username;
		this.realName = realName;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public List<ResourceTree> getResourceTrees() {
		return resourceTrees;
	}

	public void setResourceTrees(List<ResourceTree> resourceTrees) {
		this.resourceTrees = resourceTrees;
	}
}
