package com.syfri.userservice.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 验证码过滤器
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws  Exception{
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();
		//取出Session中验证码
		String code = (String) session.getAttribute("code");
		//取出输入的验证码
		String validateCode = httpServletRequest.getParameter("validateCode");
		if(code!=null && validateCode!=null && !validateCode.equals(code)){
			httpServletRequest.setAttribute("shiroLoginFailure","kaptchaValidateFailed");
			return true;
		}
		return super.onAccessDenied(request, response);
	}
}
