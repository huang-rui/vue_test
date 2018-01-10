package com.syfri.demo.controller;

import com.syfri.demo.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	private DemoService demoService;

	@RequestMapping("/test")
	public String Test(){
		return demoService.getAllUsers();
	}

}
