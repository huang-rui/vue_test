package com.syfri.userserver.service;

import com.syfri.userserver.domain.UserEntity;

import java.util.List;

public interface UserService {

	List<UserEntity> getAllUsers();
}
