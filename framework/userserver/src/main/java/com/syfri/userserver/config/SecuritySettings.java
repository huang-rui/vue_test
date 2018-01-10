package com.syfri.userserver.config;

/**
 * 自定义的配置类，此类中定义的属性均可以在properties.yml中配置
 */
//@ConfigurationProperties(prefix="securityconfig")
public class SecuritySettings {
	private String logoutsuccessurl = "/logout";
	private String permitall = "/api";
	private String deniedpage = "/deny";
	private String urlroles;

	public String getLogoutsuccessurl() {
		return logoutsuccessurl;
	}

	public void setLogoutsuccessurl(String logoutsuccessurl) {
		this.logoutsuccessurl = logoutsuccessurl;
	}

	public String getPermitall() {
		return permitall;
	}

	public void setPermitall(String permitall) {
		this.permitall = permitall;
	}

	public String getDeniedpage() {
		return deniedpage;
	}

	public void setDeniedpage(String deniedpage) {
		this.deniedpage = deniedpage;
	}

	public String getUrlroles() {
		return urlroles;
	}

	public void setUrlroles(String urlroles) {
		this.urlroles = urlroles;
	}
}
