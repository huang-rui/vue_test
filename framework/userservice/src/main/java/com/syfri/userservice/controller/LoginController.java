package com.syfri.userservice.controller;

import com.syfri.userservice.utils.ImageCodeUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.util.Date;
import java.util.Map;

@Api(value = "登录",tags = "登录API",description = "登录")
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/index")
	public String index(){
		return "/index";
	}

	@GetMapping({"/","/login"})
	public String login(){
		return "/login";
	}

	/**
	 * 此方法不处理登录成功的情况，由shiro进行处理
	 */
	@PostMapping("/login")
	public String login(HttpServletRequest request, Map<String,Object> map) throws Exception{
		String exception = (String) request.getAttribute("shiroLoginFailure");
		logger.info("【loginController】" + exception);
		String msg = "";
		if(exception != null){
			if(UnknownAccountException.class.getName().equals(exception)){
				msg = "UnknownAccountException --> 账号不存在";
			}else if(IncorrectCredentialsException.class.getName().equals(exception)){
				msg = "IncorrectCredentialsException --> 密码不正确";
			}else if(ExcessiveAttemptsException.class.getName().equals(exception)){
				msg = "ExcessiveAttemptsException --> 密码输入错误次数超过5次";
			}else if("kaptchaValidateFailed".equals(exception)){
				msg = "kaptchaValidateFailed --> 验证码错误";
			}else{
				msg = "else --> " + exception;
			}
		}
		map.put("msg",msg);
		//以下是登录错误时返回login.html页面
		return "/login";
	}

	@GetMapping("/403")
	public String unauthorizedRole(){
		logger.info("-----没有权限-----");
		return "/403";
	}

	/**
	 * 验证码
	 */
	@GetMapping("/imageCode")
	public void reloadCode(HttpServletRequest request, HttpServletResponse response) throws IOException{

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		OutputStream os = response.getOutputStream();
		Map<String,Object> map = ImageCodeUtil.getImageCode(60,20,os);
		HttpSession session = request.getSession();
		session.setAttribute("code", map.get("strEnsure").toString().toLowerCase());
		session.setAttribute("codeTime", new Date().getTime());
		try{
			ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * logout
	 */
	@RequestMapping("/logout")
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/login";
	}
}
