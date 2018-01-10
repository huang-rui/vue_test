package com.syfri.userservice.utils;

import com.syfri.userservice.model.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 * 获取当前登录用户
 * @author li.xue  2017/12/18
 */
public class CurrentUserUtil {

	//获取当前用户
	public static ShiroUser getCurrentUser(){
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}

	//获取当前用户ID
	public static String getCurrentUserId(){
		return getCurrentUser().getUserid();
	}

	//获取当前用户名
	public static String getCurrentUserName(){
		return getCurrentUser().getUsername();
	}
}
