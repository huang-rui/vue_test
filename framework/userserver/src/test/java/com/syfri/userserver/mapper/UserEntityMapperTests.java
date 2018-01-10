package com.syfri.userserver.mapper;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.syfri.userserver.controller.MyBatisController;
import com.syfri.userserver.domain.UserEntity;
import com.syfri.userserver.util.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityMapperTests {

	private Logger logger = LoggerFactory.getLogger(UserEntityMapperTests.class);

	@Autowired
	private MyBatisController myBatisController;

	@Test
	public void findByPage(){
		Page<UserEntity> users = myBatisController.findByPage();
		PageInfo<UserEntity> pageInfo = new PageInfo<>(users);
		Assert.assertNotNull(users);
		logger.debug(pageInfo.toString());
		logger.debug(JSON.toJSONString(pageInfo));
	}
}
