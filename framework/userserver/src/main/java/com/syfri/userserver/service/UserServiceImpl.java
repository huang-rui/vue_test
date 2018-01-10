package com.syfri.userserver.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.syfri.userserver.domain.UserEntity;
import com.syfri.userserver.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserEntityMapper userEntityMapper;

	@Override
	public List<UserEntity> getAllUsers() {
		return userEntityMapper.getAllUsers();
	}
}
