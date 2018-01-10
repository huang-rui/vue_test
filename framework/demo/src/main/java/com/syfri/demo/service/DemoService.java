package com.syfri.demo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.syfri.userserver.domain.UserEntity;
import com.syfri.userserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoService {

	private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

	@Reference
	UserService userService;

	public String getAllUsers(){
		List<UserEntity> list = userService.getAllUsers();
			logger.info("查询出来的第一个结果{}",list.get(0));
		return "调用成功！";
	}
}
