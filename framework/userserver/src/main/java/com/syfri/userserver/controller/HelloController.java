package com.syfri.userserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class HelloController {
	private String message = "Hello world!";

	@RequestMapping("/hello")
	public String hello(ModelMap model){
		model.put("time",new Date());
		model.put("message",message);
		return "hello";
	}

	@RequestMapping("/hello2")
	public ModelAndView hello2(){
		ModelAndView view =  new ModelAndView("hello");
		view.addObject("time",new Date());
		view.addObject("message",message);
		return view;
	}
}
