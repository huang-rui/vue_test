package com.syfri.userserver.controller;

import com.syfri.userserver.util.ImageCode;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

/**
 * 拦截器拦截初始请求跳转到登录页面/login
 * @author  li.xue  2017/11/29
 */
@Controller
public class LoginController {

	@RequestMapping("/login")
//	public ModelAndView index(){
//		ModelAndView view = new ModelAndView("login/login");
//		return view;
//	}
	public String login(){
		return "login/login";
	}

	/**
	 * 生成验证码
	 * @author  li.xue  2017/11/27
	 */
	@RequestMapping(value = "/images/imagecode" )
	public String imagecode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream os = response.getOutputStream();
		Map<String,Object> map = ImageCode.getImageCode(60,20,os);
		String simpleCaptcha = "simpleCaptcha";
		request.getSession().setAttribute(simpleCaptcha,map.get("strEnsure").toString().toLowerCase());
		request.getSession().setAttribute("codeTime",new Date().getTime());
		try{
			ImageIO.write((BufferedImage)map.get("image"),"JPEG",os);
		}catch (IOException e){
			return "";
		}
		return null;
	}

	/**
	 * 校验验证码
	 * @author  li.xue  2017/11/27
	 */
	@RequestMapping("/checkcode")
	@ResponseBody
	public String checkcode(HttpServletRequest request, HttpSession session) throws Exception {
		String checkCode = request.getParameter("checkCode");
		Object cko = session.getAttribute("simpleCaptcha") ; //验证码对象
		if(cko == null){
			request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
			return "验证码已失效，请重新输入！";
		}

		String captcha = cko.toString();
		Date now = new Date();
		Long codeTime = Long.valueOf(session.getAttribute("codeTime")+"");
		if(StringUtils.isEmpty(checkCode) || captcha == null ||  !(checkCode.equalsIgnoreCase(captcha))){
			request.setAttribute("errorMsg", "验证码错误！");
			return "验证码错误！";
		}else if ((now.getTime()-codeTime)/1000/60>5){//验证码有效时长为5分钟
			request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
			return "验证码已失效，请重新输入！";
		}else {
			session.removeAttribute("simpleCaptcha");
			return "1";
		}
	}

}
